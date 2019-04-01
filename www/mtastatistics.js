var exec = require('cordova/exec');

exports.onPageStart = function (pageName, success, error) {
    exec(success, error, "mtastatistics", "onPageStart", [pageName]);
};

exports.onPageEnd = function (pageName, success, error) {
    exec(success, error, "mtastatistics", "onPageEnd", [pageName]);
};

exports.onEvent = function (eventId, eventLabel, success, error) {
    exec(success, error, "mtastatistics", "onEvent", [eventId, eventLabel]);
};