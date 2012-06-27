define(['underscore', './stage'], function(_, Stage) {
    var Sprite = function(data, header, dictionary) {
        this.characterId = data.characterId;
        this.frameCount = data.frameCount;
        this.currentFrame = 0;
        this.frames = [];
        this.frameRate = header.frameRate;
        this.stage = new Stage(header.bounds, dictionary);
        this.data = data;
    };
    
    Sprite.prototype._onFrameAction = function(action) {
        switch(action.type) {
            case "ADD":
                this.stage.addToStage(action.depth, action.character, action.matrix, action.clipping);
                break;
            
            case "MODIFY":
                this.stage.modify(action.depth, action.matrix);
                break;
            
            case "REPLACE":
                this.stage.replace(action.depth, action.character, action.matrix);
                break;
            
            case "REMOVE":
                this.stage.remove(action.depth);
                break;
            
            case "RATIO":
                this.stage.ratio(action.depth, action.ratio);
                break;
            
            default: throw new Error("unknown type " + action.type);
        }
    };
    
    Sprite.prototype.renderFrame = function(canvas, ctx) {
        if(this.currentFrame >= this.frameCount) {
            this.onStartAnimation();
            this.currentFrame = 0;
        }
        
        _.each(this.data.frames[this.currentFrame], function(action) {
            this._onFrameAction(action);
        }, this);
        this.stage.showFrame(canvas, ctx);
        $('#debug .text').text("current frame: " + this.currentFrame);
        this.currentFrame++;
    };
    
    Sprite.prototype.onStartAnimation = function() {
        this.stage.clear();
    };
    
    return Sprite;
});