define(['underscore'], function(_) {
    
    var Shape = function(data) {
        this.characterId = data.characterId;
        this.data = data;
    };
    
    Shape.prototype._setStyle = function(ctx, command) {
        var fillStyleIndex = command.fillStyle1;
        if(fillStyleIndex !== undefined && this.fillStyle === undefined) {
            this.fillStyle = this.fillStyles[fillStyleIndex].color;
            ctx.save();
            ctx.setFillColor(this.fillStyle);
        }
        
    };
    
    Shape.prototype._onDraw = function(ctx, command) {
        switch(command.type) {
            case "STRAIGHT": 
                this._setStyle(ctx, command);
                ctx.lineTo(command.to.x, command.to.y);
                this.drawingPosition = command.to;
                break;
            
            case "CURVED": 
                this._setStyle(ctx, command);
                ctx.quadraticCurveTo(command.control.x, command.control.y, command.anchor.x, command.anchor.y);
                this.drawingPosition = command.anchor;
                break;
            
            case "MOVE":
                ctx.moveTo(command.to.x, command.to.y);
                break;
            
            case "FILL": 
                //ctx.closePath();
                if(this.fillStyle) {
                    ctx.fill();
                    ctx.restore();
                    this.fillStyle = undefined;
                }
                ctx.beginPath();
                break;
            
            case "STYLES":
                this.fillStyles = command.fillStyles;
                break;
            
            case "LINE":
                // lines delt with in a seperate pass
                break;
            
            default: throw new Error("unknown type " + command.type);
        }
    };
    
    Shape.prototype._onDrawLine = function(ctx, command) {
        switch(command.type) {
            case "STRAIGHT":
                if(this.lineStyle !== undefined) {
                    ctx.lineTo(command.to.x, command.to.y);
                }
                this.drawingPosition = command.to;
                break;
            
            case "CURVED":
                if(this.lineStyle !== undefined) {
                    ctx.quadraticCurveTo(command.control.x, command.control.y, command.anchor.x, command.anchor.y);
                }
                this.drawingPosition = command.anchor;
                break;
            
            case "MOVE":
                if(this.lineStyle !== undefined) {
                    ctx.moveTo(command.to.x, command.to.y);
                }
                this.drawingPosition = command.to;
                break;
            
            case "FILL":
                // fills delt with in a seperate pass
                break;
            
            case "STYLES":
                this.lineStyles = command.lineStyles;
                break;
            
            case "LINE":
                if(command.clear) {
                    var first = this.lineStyle === undefined;
                    this.lineStyle = undefined;
                    
                    if(!first) {
                        ctx.stroke();
                        ctx.restore();
                    }
                } else {
                    this.lineStyle = this.lineStyles[command.lineStyle];
                    ctx.save();
                    ctx.beginPath();
                    ctx.moveTo(this.drawingPosition.x, this.drawingPosition.y);
                    ctx.setStrokeColor(this.lineStyle.color);
                    ctx.setLineWidth(this.lineStyle.width);
                }
                break;
            
            default: throw new Error("unknown type " + command.type);
        }
    };
    
    Shape.prototype.draw = function(ctx, clipping) {
        if(!clipping) {
            ctx.save();
            ctx.beginPath();
            _.each(this.data.commands, function(command) {
                this._onDrawLine(ctx, command);
            }, this);
            if(this.lineStyle) {
                ctx.stroke();
                this.lineStyle = undefined;
                ctx.restore();
            }
            ctx.restore();
        }
        
        ctx.save();
        ctx.beginPath();
        _.each(this.data.commands, function(command) {
            this._onDraw(ctx, command);
        }, this);
        ctx.restore();
    };
    
    return Shape;
});