var oauth2orize = require('oauth2orize')
    , passport = require('passport')
    , crypto = require('crypto')
    , rs = require('random-strings')
    , constants = require('../models/constants')
    , utils = require('../models/utils')
    , User = require('../models/user')
    , Token = require('../models/token');

var server = oauth2orize.createServer();

server.exchange(oauth2orize.exchange.password(function (client, username, password, callback) {
    User.findOne({username: username}, function (err, userRecord) {
        if (err) { return callback(err) }
        if (!userRecord) { return callback(null, false) }
        userRecord.verifyPassword(password, function (err, isValid) {
            if (!isValid) { return callback(null, false) }

            var generatedAccessToken = rs.alphaNumMixed(256);
            var generatedRefreshToken = rs.alphaNumMixed(256);
            var accessTokenHash = crypto.createHash('sha1').update(generatedAccessToken).digest('hex')
            var refreshTokenHash = crypto.createHash('sha1').update(generatedRefreshToken).digest('hex')
            var date = new Date()
            date.setSeconds(date.getSeconds() + TOKEN_TIMETOLIVE)
            var unixtime = Math.floor(date.getTime() / 1000)

            var token = new Token({
                accessToken: accessTokenHash,
                refreshToken: refreshTokenHash,
                expirationDate: date,
                userId: userRecord.userId,
                clientId: client.clientId
            });

            token.save(function (err) {
                if (err) { return callback(err) }
                callback(null, generatedAccessToken, generatedRefreshToken, {expires_in: unixtime})
            })
        })
    })
}))

server.exchange(oauth2orize.exchange.refreshToken(function (client, refreshToken, callback) {
    var refreshTokenHash = crypto.createHash('sha1').update(refreshToken).digest('hex')

    Token.findOne({refreshToken: refreshTokenHash}, function (err, tokenRecord) {
        if (err) { return callback(err) }
        if (!tokenRecord) { return callback(null, false) }
        if (client.clientId !== tokenRecord.clientId) return callback(null, false)

        var newAccessToken = rs.alphaNumMixed(256)
        var accessTokenHash = crypto.createHash('sha1').update(newAccessToken).digest('hex')

        var date = new Date()
        date.setSeconds(date.getSeconds() + TOKEN_TIMETOLIVE)
        var expirationDate = date
        var unixtime = Math.floor(date.getTime() / 1000)

        Token.update({refreshToken: tokenRecord.refreshToken}, {$set: {token: accessTokenHash, expirationDate: expirationDate}}, function (err) {
            if (err) { return callback(err) }
            callback(null, newAccessToken, refreshToken, {expires_in: unixtime});
        })
    })
}));

exports.logout = function(req, res) {
    var accessToken = utils.getAccessToken(req.headers.authorization);
    Token.findOne({accessToken: accessToken}, function (err, tokenRecord) {
        if (err) { return callback(err) }
        if (!tokenRecord) { return callback(null, false) }
        Token.remove({accessToken: accessToken}, function (err, response) {
            if(err) {res.send(err)
            } else {
                res.send({valid: "ok"})
            }})
    })
};

exports.token = [
    server.token(),
    server.errorHandler()
];
