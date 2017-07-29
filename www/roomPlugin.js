var argscheck = require('cordova/argscheck'),
    channel = require('cordova/channel'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec'),
    cordova = require('cordova');

channel.createSticky('onCordovaInfoReady');
channel.waitForInitialization('onCordovaInfoReady');

let PLUGIN_NAME = "RoomPlugin";

let errorCallback = function(e) {
    utils.alert("Handled Server Error: " + e);
}

let clientErrorCallback = function(e) {
    utils.alert("Handled Client Error: " + e);
}

function RoomPlugin() {
    this.callback;

     channel.onCordovaReady.subscribe(function() {
          channel.onCordovaInfoReady.fire();
     });
}

RoomPlugin.prototype.insert = function(user,successCallback) {
    if(!(user.uid.trim() == "" && user.firstName.trim() == "" && user.lastName.trim() == "")){
        if(user.uid.trim() == "" || Number.isInteger(parseInt(user.uid))){
                exec(successCallback, errorCallback, PLUGIN_NAME, 'insert', [user]);
            } else {
                clientErrorCallback("Explicit user id must be an integer value")
        }
    }

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