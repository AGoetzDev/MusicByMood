(function () {
    'use strict';

    angular
        .module('app')
        .factory('ImageService', ImageService);

    
    function ImageService() {
        var service = {};
        service.config = {};
        service.config.quality = 1.0;
        service.config.maxWidth = 800;
        service.config.maxHeight = 800;

        service.scaleImage = scaleImage;
        

        return service;

        
        function scaleImage(file, callback) {
        	var img = document.createElement("img");
        	var imageData;
        	
        	var reader = new FileReader();  
        	reader.onload = function(e) {
        		img.src = e.target.result
        		
        		img.onload = function () {
	        		var canvas = document.createElement('canvas');
	        		var ctx = canvas.getContext("2d");
	        		canvas.width = img.width;
	                canvas.height = img.height;
	                ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
	
	                while (canvas.width >= (2 * service.config.maxWidth)) {
	                     canvas = getHalfScaleCanvas(canvas);
	                 }
	
	                 if (canvas.width > service.config.maxWidth) {
	                     canvas = scaleCanvasWithAlgorithm(canvas);
	                 } 
	
	                 imageData = canvas.toDataURL('image/jpeg', service.config.quality);
	                 callback(imageData); 
        		}
        		
        	}
        	reader.readAsDataURL(file);

        };

         function scaleCanvasWithAlgorithm (canvas) {
            var scaledCanvas = document.createElement('canvas');

            var scale = service.config.maxWidth / canvas.width;

            scaledCanvas.width = canvas.width * scale;
            scaledCanvas.height = canvas.height * scale;

            var srcImgData = canvas.getContext('2d').getImageData(0, 0, canvas.width, canvas.height);
            var destImgData = scaledCanvas.getContext('2d').createImageData(scaledCanvas.width, scaledCanvas.height);

            applyBilinearInterpolation(srcImgData, destImgData, scale);

            scaledCanvas.getContext('2d').putImageData(destImgData, 0, 0);

            return scaledCanvas;
        };

        function getHalfScaleCanvas(canvas) {
            var halfCanvas = document.createElement('canvas');
            halfCanvas.width = canvas.width / 2;
            halfCanvas.height = canvas.height / 2;

            halfCanvas.getContext('2d').drawImage(canvas, 0, 0, halfCanvas.width, halfCanvas.height);

            return halfCanvas;
        };

       function applyBilinearInterpolation(srcCanvasData, destCanvasData, scale) {
            function inner(f00, f10, f01, f11, x, y) {
                var un_x = 1.0 - x;
                var un_y = 1.0 - y;
                return (f00 * un_x * un_y + f10 * x * un_y + f01 * un_x * y + f11 * x * y);
            }
            var i, j;
            var iyv, iy0, iy1, ixv, ix0, ix1;
            var idxD, idxS00, idxS10, idxS01, idxS11;
            var dx, dy;
            var r, g, b, a;
            for (i = 0; i < destCanvasData.height; ++i) {
                iyv = i / scale;
                iy0 = Math.floor(iyv);
                // Math.ceil can go over bounds
                iy1 = (Math.ceil(iyv) > (srcCanvasData.height - 1) ? (srcCanvasData.height - 1) : Math.ceil(iyv));
                for (j = 0; j < destCanvasData.width; ++j) {
                    ixv = j / scale;
                    ix0 = Math.floor(ixv);
                    // Math.ceil can go over bounds
                    ix1 = (Math.ceil(ixv) > (srcCanvasData.width - 1) ? (srcCanvasData.width - 1) : Math.ceil(ixv));
                    idxD = (j + destCanvasData.width * i) * 4;
                    // matrix to vector indices
                    idxS00 = (ix0 + srcCanvasData.width * iy0) * 4;
                    idxS10 = (ix1 + srcCanvasData.width * iy0) * 4;
                    idxS01 = (ix0 + srcCanvasData.width * iy1) * 4;
                    idxS11 = (ix1 + srcCanvasData.width * iy1) * 4;
                    // overall coordinates to unit square
                    dx = ixv - ix0;
                    dy = iyv - iy0;
                    // I let the r, g, b, a on purpose for debugging
                    r = inner(srcCanvasData.data[idxS00], srcCanvasData.data[idxS10], srcCanvasData.data[idxS01], srcCanvasData.data[idxS11], dx, dy);
                    destCanvasData.data[idxD] = r;

                    g = inner(srcCanvasData.data[idxS00 + 1], srcCanvasData.data[idxS10 + 1], srcCanvasData.data[idxS01 + 1], srcCanvasData.data[idxS11 + 1], dx, dy);
                    destCanvasData.data[idxD + 1] = g;

                    b = inner(srcCanvasData.data[idxS00 + 2], srcCanvasData.data[idxS10 + 2], srcCanvasData.data[idxS01 + 2], srcCanvasData.data[idxS11 + 2], dx, dy);
                    destCanvasData.data[idxD + 2] = b;

                    a = inner(srcCanvasData.data[idxS00 + 3], srcCanvasData.data[idxS10 + 3], srcCanvasData.data[idxS01 + 3], srcCanvasData.data[idxS11 + 3], dx, dy);
                    destCanvasData.data[idxD + 3] = a;
                }
            }
        };

        
    }

})();
