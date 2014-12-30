var Client = require('../models/client')
    , rs = require('random-strings');

exports.createClient = function(req, res) {
    var pair = {
        id: rs.alphaNumMixed(25),
        secret: rs.alphaNumMixed(50)
    }

    var client = new Client({
        name: req.body.name,
        clientId: pair.id,
        clientSecret: pair.secret,
        authorized: true
    });

    var query = Client.find();
    query.count(function(err, count){
        if(count == 0) {
            client.save(function (err) {
                if (err) { res.send(err) }
                else { res.json(pair) }
            });
        } else {
            res.json({"valid": "notok"})
        }
    });
};