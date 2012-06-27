define(['jquery', 'underscore', './shape', './sprite', './morph', './sprite-player'], function($, _, Shape, Sprite, Morph, SpritePlayer) {
    
    var createDictionary = function (data) {
        var header = {};
        var dictionary = [];
        
        var add = function(character) {
            dictionary[character.characterId] = character;
        };
        
        var onTag = function(tag) {
            switch(tag.type) {
                case "HEADER":
                    header = tag;
                    break;

                case "SHAPE":
                    var shape = new Shape(tag);
                    add(shape);
                    break;

                case "SPRITE":
                    var sprite = new Sprite(tag, header, dictionary);
                    add(sprite);
                    break;

                case "MORPH":
                    var morph = new Morph(tag);
                    add(morph);
                    break;

                default: throw new Error("unknown type " + tag.type);
            }
        };

        _.each(data, onTag);
        
        return dictionary;
    };
    
    var SwfJs = function(data) {
        this.dictionary = createDictionary(data);
    };
    
    SwfJs.prototype.createSpritePlayer = function(characterId, canvasElementId) {
        var canvas = $('#' + canvasElementId).get(0);
        var ctx = canvas.getContext('2d');
        var sprite = this.dictionary[characterId];
        return new SpritePlayer(sprite, canvas, ctx);
    };
    
    return SwfJs;
});