var Post = require('../models/post')
    , Token = require('../models/token')
    , utils = require('../models/utils')
    , rs = require('random-strings');

exports.createPost = function(req, res) {
    var accessToken = utils.getAccessToken(req.headers.authorization);
    Token.findOne({ accessToken: accessToken }, function(err, tokenRecord) {
        if (err) { res.send(err) }
        else {
            var post = new Post({
                userId: tokenRecord.userId,
                type: req.body.type,
                location: [req.body.lon, req.body.lat],
                images: req.body.images,
                metadata: {
                    specie: req.body.specie,
                    size: req.body.size,
                    color: req.body.color,
                    age: req.body.age,
                    observations: req.body.observations,
                    identification: req.body.identification
                }
            });

            post.save(function (err) {
                if (err) { res.send(err) }
                else { res.json({"valid": "ok"}) }
            });
        }
    });
};

exports.getPost = function(req, res) {
    Post.findOne({ postId: req.params.post_id }, function(err, post) {
       if(err) { res.send(err) }
        else { res.send(post) }
    });
};

exports.getPosts = function(req, res) {
    Post.find({ }, function(err, posts) {
        if(err) { res.send(err) }
        else { res.send(posts) }
    });
};

exports.myPosts = function(req, res) {
    var accessToken = utils.getAccessToken(req.headers.authorization)
    Token.findOne({ accessToken: accessToken }, function(err, tokenRecord) {
        if(err) { res.send(err) }
        else {
            Post.find({ userId: tokenRecord.userId }, function (err, posts) {
                if (err) { res.send(err) }
                else { res.send(posts) }
            });
        }
    });
};

