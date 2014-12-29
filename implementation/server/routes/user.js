var User = require('../models/user');

exports.createUser = function(req, res) {
    var user = new User({
        username: req.body.username,
        email: req.body.email,
        phoneNumber: req.body.phoneNumber,
        password: req.body.password
    });

    user.save(function(err){
        if(err) { res.send(err.message); }
        else { res.json({ response: "ok" }) };
    });
};