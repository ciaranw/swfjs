define(['underscore'], function(_) {
    var Stage = function(bounds, dictionary) {
        this.dictionary = dictionary;
        this.bounds = bounds;
        this.layers = {};
    };
    
    Stage.prototype.addToStage = function(layer, characterId, matrix, clipping) {
        if(matrix === undefined) {
            matrix = this.layers[layer].matrix;
        }
        
        this.layers[layer] = {
            characterId: characterId,
            clipping: clipping,
            matrix: matrix,
            ratio: 0
        };
    };
    
    Stage.prototype.modify = function(layer, matrix) {
        if(matrix !== undefined) {
            this.layers[layer].matrix = matrix;
        }
    };
    
    Stage.prototype.replace = function(layer, characterId, matrix) {
        this.addToStage(layer, characterId, matrix);
    };
    
    Stage.prototype.remove = function(layer) {
        delete this.layers[layer];
    };
    
    Stage.prototype.clear = function() {
        this.layers = {};
    };
    
    Stage.prototype.ratio = function(layer, ratio) {
        this.layers[layer].ratio = ratio;
    };
    
    Stage.prototype.showFrame = function(canvas, ctx) {
        ctx.save();
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.translate(200, 200);
        
        _.chain(this.layers)
            .sortBy(function(value, key) { 
                return parseInt(key, 10); 
            })
            .each(function(value) {
                var matrix = value.matrix;
                ctx.save();
                ctx.transform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
                var character = this.dictionary[value.characterId];
                if(character == undefined) {
                    console.log("undefined character with id " + value.characterId);
                } else {
                    character.draw(ctx, value.clipping, value.ratio);
                }
                ctx.restore();
            }, this)
            .value();
        
        ctx.restore();
    };
    
    return Stage;
});