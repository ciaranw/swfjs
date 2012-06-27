define(function() {
    var SpritePlayer = function(sprite, canvas, ctx) {
        this.sprite = sprite;
        this.canvas = canvas;
        this.ctx = ctx;
    };
    
    SpritePlayer.prototype.play = function() {
        var self = this;
        function render() {
            self.advanceFrame();
            self.timeoutId = setTimeout(render, 1000 / self.sprite.frameRate);
        }
        
        render();
    };
    
    SpritePlayer.prototype.isPlaying = function() {
        return this.timeoutId !== undefined;
    }
    
    SpritePlayer.prototype.stop = function() {
        if(this.timeoutId) {
            clearTimeout(this.timeoutId);
            this.timeoutId = undefined;
        }
    };
    
    SpritePlayer.prototype.advanceFrame = function() {
        this.sprite.renderFrame(this.canvas, this.ctx);
    };
    
    return SpritePlayer;
});