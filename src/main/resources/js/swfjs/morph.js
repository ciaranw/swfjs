define(['underscore', './shape'], function(_, Shape) {
    
    var Morph = function(data) {
        this.characterId = data.characterId;
        this.data = data;
        this.startShape = new Shape({
            characterId: this.characterId,
            commands: this.data.start.commands
        });
        this.endShape = new Shape({
            characterId: this.characterId,
            commands: this.data.start.commands
        });
    };
    
    Morph.prototype._setFillStyle = function(ctx, command) {
        var fillStyleIndex = command.fillStyle1;
        if(fillStyleIndex !== undefined && this.fillStyle === undefined) {
            this.fillStyle = this.fillStyles[fillStyleIndex].color;
            ctx.setFillColor(this.fillStyle);
        }
    };
    
    Morph.prototype._calculateRatio = function(start, end, ratio) {
        return ((end - start) / 100) * ratio + start;
    };
    
    Morph.prototype._onMatchingEdges = function(ctx, ratio, start, end) {
        switch(start.type) {
            case "STRAIGHT":
                this._setFillStyle(ctx, start);
                var x = this._calculateRatio(start.to.x, end.to.x, ratio);
                var y = this._calculateRatio(start.to.y, end.to.y, ratio);
                ctx.lineTo(x, y);
                break;
            
            case "CURVED":
                this._setFillStyle(ctx, start);
                var controlX = this._calculateRatio(start.control.x, end.control.x, ratio);
                var controlY = this._calculateRatio(start.control.y, end.control.y, ratio);
                var anchorX = this._calculateRatio(start.anchor.x, end.anchor.x, ratio);
                var anchorY = this._calculateRatio(start.anchor.y, end.anchor.y, ratio);
                ctx.quadraticCurveTo(controlX, controlY, anchorX, anchorY);
                break;
            
            case "MOVE":
                var x = this._calculateRatio(start.to.x, end.to.x, ratio);
                var y = this._calculateRatio(start.to.y, end.to.y, ratio);
                ctx.moveTo(x, y);
                break;
            
            case "FILL":
                ctx.fill();
                ctx.setFillColor("rgba(255, 255, 255, 0)");
                this.fillStyle = undefined;
                ctx.beginPath();
                break;

            case "STYLES":
                this.fillStyles = start.fillStyles;
                break;
            
            case "LINE":
                break;
            
            default: throw new Error("unknown type " + start.type);
        }
    };
    
    Morph.prototype._onMatchingLineEdges = function(ctx, ratio, start, end) {
        switch(start.type) {
            case "STRAIGHT":
                var x = this._calculateRatio(start.to.x, end.to.x, ratio);
                var y = this._calculateRatio(start.to.y, end.to.y, ratio);
                if(this.lineStyle !== undefined) {
                    ctx.lineTo(x, y);
                }
                this.drawingPosition = {x: x, y: y};
                break;
            
            case "CURVED":
                var anchorX = this._calculateRatio(start.anchor.x, end.anchor.x, ratio);
                var anchorY = this._calculateRatio(start.anchor.y, end.anchor.y, ratio);
                if(this.lineStyle !== undefined) {
                    var controlX = this._calculateRatio(start.control.x, end.control.x, ratio);
                    var controlY = this._calculateRatio(start.control.y, end.control.y, ratio);
                    ctx.quadraticCurveTo(controlX, controlY, anchorX, anchorY);
                }
                this.drawingPosition = {x: anchorX, y: anchorY};
                break;
            
            case "MOVE":
                var x = this._calculateRatio(start.to.x, end.to.x, ratio);
                var y = this._calculateRatio(start.to.y, end.to.y, ratio);
                if(this.lineStyle !== undefined) {
                    ctx.moveTo(x, y);
                }
                this.drawingPosition = {x: x, y: y};
                break;
            
            case "FILL":
                // fills delt with in a seperate pass
                break;
            
            case "STYLES":
                this.lineStyles = start.lineStyles;
                break;
            
            case "LINE":
                if(start.clear) {
                    this.lineStyle = undefined;
                    ctx.stroke();
                    ctx.setStrokeColor("rgba(255,255,255,0)");
                    ctx.setLineWidth(0);
                } else {
                    this.lineStyle = this.lineStyles[start.lineStyle];
                    ctx.beginPath();
                    ctx.moveTo(this.drawingPosition.x, this.drawingPosition.y);
                    ctx.setStrokeColor(this.lineStyle.color);
                    ctx.setLineWidth(this.lineStyle.width);
                }
                break;
            
            default: throw new Error("unknown type " + command.type);
        }
    };
    
    Morph.prototype._onNonMatchingEdges = function(ctx, ratio, start, end, drawFunc) {
        function convertToCurved(edge) {
            if(edge.type === "STRAIGHT") {
                return {
                    type: "CURVED",
                    control: {
                        x: edge.to.x / 2,
                        y: edge.to.y / 2
                    },
                    anchor: {
                        x: edge.to.x,
                        y: edge.to.y
                    }
                }
            } else {
                return edge;
            }
        }
        
        start = convertToCurved(start);
        end = convertToCurved(end);
        
        drawFunc.call(this, ctx, ratio, start, end);
    };
    
    Morph.prototype._onDraw = function(ctx, ratio, start, end, drawFunc) {
        if(start.type === end.type) {
            drawFunc.call(this, ctx, ratio, start, end);
        } else {
            this._onNonMatchingEdges(ctx, ratio, start, end, drawFunc);
        }
    };
    
    Morph.prototype.draw = function(ctx, clipping, ratio) {
        var percent = ratio / 65535;
        if(ratio === 0) {
            this.startShape.draw(ctx);
        } else if(ratio === 100) {
            this.endShape.draw(ctx);
        } else { 
            var zipped = _.zip(this.data.start.commands, this.data.end.commands);
            
            ctx.save();
            ctx.beginPath();
            _.each(zipped, function(e) {
                this._onDraw(ctx, percent, e[0], e[1], this._onMatchingLineEdges);
            }, this);
            
            if(this.lineStyle) {
                ctx.stroke();
                this.lineStyle = undefined;
            }
            ctx.restore();
            
            ctx.save();
            ctx.beginPath();
            _.each(zipped, function(e) {
                this._onDraw(ctx, percent, e[0], e[1], this._onMatchingEdges);
            }, this);
            ctx.restore();
            
        }
    };
    
    return Morph;
});