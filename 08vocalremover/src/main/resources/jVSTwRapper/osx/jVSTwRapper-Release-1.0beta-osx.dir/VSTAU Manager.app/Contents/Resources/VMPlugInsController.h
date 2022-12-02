//
//  VMPlugInsController.h
//  VSTAU
//
//  Created by Seth Kingsley on 12/14/07.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface VMPlugInsController : NSArrayController
{
@protected
	IBOutlet id _delegate;
}

- (id)delegate;
- (void)setDelegate:(id)delegate;
- (void)setStatus:(enum VMPlugInStatus)status ofPlugIns:(NSArray *)plugIns;

@end

@interface NSObject (VMPlugInsControllerDelegate)

- (void)plugInsController:(VMPlugInsController *)controller
			willSetStatus:(enum VMPlugInStatus)stats
				ofPlugIns:(NSArray *)plugIns;
- (void)plugInsController:(VMPlugInsController *)controller
			willSetStatus:(enum VMPlugInStatus)status
				 ofPlugIn:(VMPlugIn *)plugIn;
- (void)plugInsController:(VMPlugInsController *)controller
			 didSetStatus:(enum VMPlugInStatus)status
				 ofPlugIn:(VMPlugIn *)plugIn;
- (void)plugInsController:(VMPlugInsController *)controller
			 didSetStatus:(enum VMPlugInStatus)status
				ofPlugIns:(NSArray *)plugIns;

@end
