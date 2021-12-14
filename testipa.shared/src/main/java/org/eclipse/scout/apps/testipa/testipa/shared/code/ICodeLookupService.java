package org.eclipse.scout.apps.testipa.testipa.shared.code;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface ICodeLookupService extends ILookupService<Long> {

}
