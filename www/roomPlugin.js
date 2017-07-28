var argscheck = require('cordova/argscheck'),
    channel = require('cordova/channel'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec'),
    cordova = require('cordova');

channel.createSticky('onCordovaInfoReady');
channel.waitForInitialization('onCordovaInfoReady');

let PLUGIN_NAME = "RoomPlugin";

let errorCallback = function(e) {
    utils.alert("[ERROR] Error: " + e);
}

function RoomPlugin() {
    this.callback;

     channel.onCordovaReady.subscribe(function() {
          channel.onCordovaInfoReady.fire();
     });
}

RoomPlugin.prototype.insert = function(user,successCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, 'insert', [user]);
}

RoomPlugin.prototype.getAll = function(successCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, 'getAll', []);
}

RoomPlugin.prototype.find = function(user, successCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, 'find', [user]);
}

RoomPlugin.prototype.delete = function(user, successCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, 'delete', [user]);
}

module.exports = new RoomPlugin();
