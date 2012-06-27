require(function() {
    var DebugCanvasContext = function(ctx) {
        return {
            save: function() {
                console.log('save');
                ctx.save();
            },
            restore: function() {
                console.log('restore');
                ctx.restore();
            },
            beginPath: function() {
                console.log('beginPath');
                ctx.beginPath();
            },
            lineTo: function(x, y) {
                ctx.lineTo(x, y);
            },
            quadraticCurveTo: function(cx, cy, ax, ay) {
                ctx.quadraticCurveTo(cx, cy, ax, ay);
            },
            moveTo: function(x, y) {
                console.log('move');
                ctx.moveTo(x, y);
            },
            fill: function() {
                console.log('fill');
                ctx.fill();
            },
            stroke: function() {
                console.log('stroke');
                ctx.stroke();
            },
            setFillColor: function(color) {
                console.log('fillColor ' + color);
                ctx.setFillColor(color);
            },
            setStrokeColor: function(color) {
                console.log('strokeColor ' + color);
                ctx.setStrokeColor(color);
            },
            setLineWidth: function(width) {
                ctx.setLineWidth(width);
            },
            transform: function(a,b,c,d,e,f) {
                console.log('transform');
                ctx.transform(a,b,c,d,e,f);
            },
            translate: function(x, y) {
                ctx.translate(x, y);
            },
            clearRect: function(x, y, w, h) {
                ctx.clearRect(x, y, w, h);
            }
        };
    };
    
    return DebugCanvasContext;
});