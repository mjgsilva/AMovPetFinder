var mongoose = require('mongoose');

var tokenSchema = new mongoose.Schema({
    accessToken: {
        type: String,
        required: true
    },
    refreshToken: {
        type: String,
        required: true
    },
    expirationDate: {
        type: Date,
        required: true
    },
    userId: {
        type: String,
        required: true
    },
    clientId: {
        type: String,
        required: true
    }
});

tokenSchema.index({ accessToken: 1 });

module.exports = mongoose.model('Token', tokenSchema);