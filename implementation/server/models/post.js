var mongoose = require('mongoose')
    , autoIncrement = require('mongoose-auto-increment')
    , constants = require('constants');

var postSchema = new mongoose.Schema({
    userId: {
        type: String,
        required: true
    },
    type: {
        type: String,
        required: true
    },
    publicationDate: {
        type: Date,
        required: true,
        default: Date.now,
        expires: POST_TIMETOLIVE
    },
    location: {
        type: [Number],
        required: true
    },
    images: {
        type: [String]
    },
    metadata: {
        specie: {
            type: String,
            required: true
        },
        size: {
            type: String,
            required: true
        },
        color: {
            type: Array,
            default: [],
            required: true
        },
        age: {
            type: String
        },
        observations: {
            type: String
        },
        identification: {
            type: String
        }
    }
});


postSchema.index({ loc: '2d' });

autoIncrement.initialize(mongoose.connection);

postSchema.plugin(autoIncrement.plugin, { model: 'Post', field: 'postId', startAt: 1, incrementBy: 1 });

module.exports = mongoose.model('Post', postSchema);