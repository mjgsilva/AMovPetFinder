var express = require('express');
var https = require('https');
var fs = require('fs');
var passport = require('passport');
var bodyParser = require('body-parser');
var dbModel = require('./models/db');
var userController = require('./routes/user');
var clientController = require('./routes/client');
var authController = require('./routes/auth');
var oauthController = require('./routes/oauth');
var postController = require('./routes/post');

var app = express();
var nodekey = fs.readFileSync('certs/nodekey.pem');
var nodecert = fs.readFileSync('certs/nodecert.pem');

var certs = {
    key: nodekey,
    cert: nodecert
};

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(passport.initialize());
var router = express.Router();

router.route('/user')
    .post(userController.createUser);

router.route('/client')
    .post(clientController.createClient);

router.route('/post')
    .post(authController.isBearerAuthenticated,postController.createPost)
    .get(authController.isBearerAuthenticated,postController.getPosts);

router.route('/post/:post_id')
    .get(authController.isBearerAuthenticated,postController.getPost)
    .delete(authController.isBearerAuthenticated,postController.removePost);

router.route('/myPosts')
    .get(authController.isBearerAuthenticated,postController.myPosts);

router.route('/find')
    .post(authController.isBearerAuthenticated,postController.findPost)

router.route('/oauth/token')
    .post(authController.isClientAuthenticated,oauthController.token);

router.route('/oauth/logout')
    .post(authController.isBearerAuthenticated,oauthController.logout);

app.use('/api', router);

https.createServer(certs, app).listen(4000);
