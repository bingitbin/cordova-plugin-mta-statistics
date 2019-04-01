/********* MTAAppMonitorStatistics.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import "MTA.h"

@interface MTAStatisticsPlugin : CDVPlugin {
  // Member variables go here.
    NSString* APP_ID;
}

- (void)onPageStart:(CDVInvokedUrlCommand*)command;
@end

@implementation MTAStatisticsPlugin

- (void)pluginInitialize {
    [self.commandDelegate runInBackground:^{
        CDVViewController *viewController = (CDVViewController *)self.viewController;
        APP_ID = [viewController.settings objectForKey:@"appid"];
        [self initMTAStatistics];
    }];
}

- (void)initMTAStatistics {
    [MTA startWithAppkey:APP_ID];
    [MTA setEnableDebugOn:YES];
}

- (void)onPageStart:(CDVInvokedUrlCommand*)command
{
    [self.commandDelegate runInBackground:^{
        CDVPluginResult* result= nil;
        NSArray* args=command.arguments;
        
        if (args.count != 1) {
            result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"please pass page name"];
        }
        else {
            [MTA trackPageViewBegin:[command argumentAtIndex:0]];
            result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        }
        
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

- (void)onPageEnd:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        CDVPluginResult* result= nil;
        NSArray* args=command.arguments;
        
        if (args.count != 1) {
            result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"please pass page name"];
        }
        else {
            [MTA trackPageViewEnd:[command argumentAtIndex:0]];
            result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        }
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

-(void)onEvent:(CDVInvokedUrlCommand *)command {
    CDVPluginResult* result= nil;
    NSArray* args=command.arguments;
    
    if (args.count != 2) {
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"please pass event and label"];
    }
    else {
        [MTA trackCustomKeyValueEvent:[command argumentAtIndex:0] props:nil];
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
