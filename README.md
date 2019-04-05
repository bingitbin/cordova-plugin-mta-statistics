# 腾讯移动统计Cordova插件

###### ps: 该插件只支持基础分析

### 安装
    cordova plugin add cordova-plugin-mtastatistics --variable IOSAPPID=[your iOS AppKey] --variable ANDROIDAPPID=[your Android AppKey]

###### 安装示例
   cordova plugin add cordova-plugin-mtastatistics --variable IOSAPPID=KKGI68BJ94YT --variable ANDROIDAPPID=M7FB4W1CUI8K

### API
cordova.plugins.MTAStatistics.onPageStart(pageName, successFn, errorFn)

cordova.plugins.MTAStatistics.onPageEnd(pageName, successFn, errorFn)

cordova.plugins.MTAStatistics.onEvent(eventId, eventLabel, successFn, errorFn)

### 使用简单示范
router.afterEach((to,from) => {

    cordova.plugins.MTAStatistics.onPageStart(to.fullPath);
    cordova.plugins.MTAStatistics.onPageEnd(from.fullPath)；
    
})
