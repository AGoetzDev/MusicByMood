package org.alexgdev.musicbymood.service;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.alexgdev.musicbymood.entities.Emotion;
import org.alexgdev.musicbymood.exception.ServiceException;
import org.alexgdev.musicbymood.util.ImageUtil;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FERService {
	
	private static final Logger log = LoggerFactory.getLogger(FERService.class);
	
	final int height = 42;
    final int width = 42;
    final int channels = 1;
	
	private MultiLayerNetwork model1;
	private NativeImageLoader imgLoader;
	private DataNormalization scaler;
	private HashMap<Integer, Emotion> emotionmap;
	
	@PostConstruct
	public void init() throws IOException{
		
		InputStream inputStream = this.getClass().getResourceAsStream("/FERModel.zip");
		
		model1 = ModelSerializer.restoreMultiLayerNetwork(inputStream);
		
		inputStream.close();
		
		imgLoader = new NativeImageLoader(height, width, channels, true);
		
		scaler = new ImagePreProcessingScaler(0,1);
		emotionmap = new HashMap<Integer, Emotion>();
		emotionmap.put(0, Emotion.anger);
		emotionmap.put(1, Emotion.disgust);
		emotionmap.put(2, Emotion.fear);
		emotionmap.put(3, Emotion.joy);
		emotionmap.put(4, Emotion.sadness);
		emotionmap.put(5, Emotion.surprise);
		emotionmap.put(6, Emotion.neutral);
	}
	
	public Emotion classifyImage(BufferedImage img) throws ServiceException{
		try {
			
			INDArray image = imgLoader.asMatrix(img);
			scaler.transform(image);
			INDArray output = model1.output(image);
			
			int max1 = this.getMaxIndex(output);
			log.info("Before Histogram Eq. Model1" + output.toString());
			
			//Check if image is grey scale
			Raster ras = img.getRaster();
	        int channels = ras.getNumDataElements();
	        if(channels>1){
	        	//make Image grey scale
	        	img = ImageUtil.makeGray(img);
	        	
	        }
		    ImageUtil.equalizeHistogram(img);
			image = imgLoader.asMatrix(img);
			scaler.transform(image);
			
			INDArray output2 = model1.output(image);
			int max2 = this.getMaxIndex(output2);
			log.info("After Histogram Eq. Model1: " + output2.toString());
			
			Emotion e;
			if(max1 == max2 || (output.getDouble(max1)>= output2.getDouble(max2))){
				 e = emotionmap.get(max1);
				
			} else {
				 e = emotionmap.get(max2);
			}
			log.info(e.name());
			return e;
			
			
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Emotion classifyBase64Image(String dataUrl) throws ServiceException{
		if(!dataUrl.startsWith("data:image/jpeg;base64,")){
			throw new ServiceException("Invalid Data Url!");
		}
		String encodingPrefix = "base64,";
		int contentStartIndex = dataUrl.indexOf(encodingPrefix) + encodingPrefix.length();
		byte[] imageData = Base64.getMimeDecoder().decode(dataUrl.substring(contentStartIndex));
		if(imageData.length ==0){
			throw new ServiceException("No Image data sent");
		}
		try(ByteArrayInputStream bis = new ByteArrayInputStream(imageData)){
			BufferedImage img = ImageIO.read(bis);
			return this.classifyImage(img);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}
	
	private int getMaxIndex(INDArray array){
		
		int maxIndex = 0;
		Double value;
	    for (int i = 1; i < array.length(); i++) {
	        value = array.getDouble(i);
	        if ((value > array.getDouble(maxIndex))) {
	            maxIndex = i;
	        }
	    }
	    return maxIndex;
	}
	
	
	
	
	

}
