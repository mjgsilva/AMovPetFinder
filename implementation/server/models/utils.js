var crypto = require('crypto');

exports.getAccessToken = function(bearer) {
    var split = bearer.split(" ")
    var accessToken = split[1];
    return crypto.createHash('sha1').update(accessToken).digest('hex')
};