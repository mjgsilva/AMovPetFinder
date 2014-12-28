var mongoose = require('mongoose')
    , async = require('async');

var clientSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    clientId: {
        type: String,
        required: true
    },
    clientSecret: {
        type: String,
        required: true
    },
    authorized: {
        type: Boolean,
        required: true
    }
});

clientSchema.index({ name: 1 });

module.exports = mongoose.model('Client', clientSchema);