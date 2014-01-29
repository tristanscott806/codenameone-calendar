#import <Foundation/Foundation.h>

@interface com_codename1_calendar_impl_CalendarNativeInterfaceImpl : NSObject {
}

-(NSString*)getEvents:(long long)param param1:(long long)param1;
-(NSString*)saveEvent:(NSString*)param param1:(NSString*)param1 param2:(long long)param2 param3:(long long)param3 param4:(BOOL)param4 param5:(BOOL)param5 param6:(NSString*)param6 param7:(NSString*)param7 param8:(NSString*)param8;
-(void)deregisterForEventNotifications;
-(void)registerForEventNotifications;
-(BOOL)hasPermissions;
-(NSString*)getEventByIdentifier:(NSString*)param;
-(BOOL)removeEvent:(NSString*)param;
-(BOOL)isSupported;
@end
