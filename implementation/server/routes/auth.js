var passport = require('passport')
    , BasicStrategy = require('passport-http').BasicStrategy
    , ClientPasswordStrategy = require('passport-oauth2-client-password').Strategy
    , BearerStrategy = require('passport-http-bearer').Strategy
    , crypto = require('crypto')
    , Client = require('../models/client')
    , User = require('../models/user')
    , Token = require('../models/token');

passport.use("clientBasic", new BasicStrategy(
    function(clientId, clientSecret, callback) {
        Client.findOne({ clientId: clientId }, function (err, clientRecord) {
            if (err) { return callback(err) }
            if (!clientRecord) { return callback(null, false) }
            if (!clientRecord.authorized) { return callback(null, false) }
            if (clientRecord.clientSecret == clientSecret) { return callback(null, clientRecord) }
            else { return callback(null, false) }
        });
    }
));

passport.use("clientPassword", new ClientPasswordStrategy(
    function(clientId, clientSecret, callback) {
        Client.findOne({ clientId: clientId }, function (err, clientRecord) {
            if (err) { return callback(err) }
            if (!clientRecord) { return callback(null, false) }
            if (!clientRecord.authorized) { return callback(null, false) }
            if (clientRecord.clientSecret == clientSecret) { return callback(null, client) }
            else { return callback(null, false) }
        });
    }
));

passport.use("accessToken", new BearerStrategy(
    function (accessToken, callback) {
        var accessTokenHash = crypto.createHash('sha1').update(accessToken).digest('hex')
        Token.findOne({accessToken: accessTokenHash}, function (err, tokenRecord) {
            if (err) { return callback(err) }
            if (!tokenRecord) { return callback(null, false) }
            if (new Date() > tokenRecord.expirationDate) {
                Token.remove({accessToken: accessTokenHash}, function (err) { callback(err) })
            } else {
                User.findOne({userId: tokenRecord.userId}, function (err, userRecord) {
                    if (err) { return callback(err) }
                    if (!userRecord) { return callback(null, false) }
                    callback(null, userRecord);
                })
            }
        })
    }
));

exports.isClientAuthenticated = passport.authenticate(['clientBasic', 'clientPassword'], { session: false });
exports.isBearerAuthenticated = passport.authenticate('accessToken', { session: false });