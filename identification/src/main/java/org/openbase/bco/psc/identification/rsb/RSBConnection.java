package org.openbase.bco.psc.identification.rsb;

/*-
 * #%L
 * BCO PSC Identification
 * %%
 * Copyright (C) 2016 - 2019 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import org.openbase.bco.psc.lib.jp.*;
import org.openbase.bco.psc.lib.jp.JPIntentScope;
import org.openbase.bco.psc.lib.rsb.AbstractRSBDualConnection;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InitializationException;
import org.openbase.jul.extension.rsb.com.RSBFactoryImpl;
import org.openbase.jul.extension.rsb.iface.RSBInformer;
import org.openbase.jul.extension.rsb.iface.RSBListener;
import org.slf4j.LoggerFactory;
import rsb.AbstractEventHandler;
import rsb.Scope;
import org.openbase.type.domotic.unit.UnitProbabilityCollectionType.UnitProbabilityCollection;
import org.openbase.type.tracking.PointingRay3DFloatDistributionCollectionType.PointingRay3DFloatDistributionCollection;

/**
 * This class handles the RSB connections of the project.
 *
 * @author <a href="mailto:thuppke@techfak.uni-bielefeld.de">Thoren Huppke</a>
 */
public class RSBConnection extends AbstractRSBDualConnection<UnitProbabilityCollection> {

    /**
     * Logger instance.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RSBConnection.class);

    /**
     * Constructor.
     *
     * @param handler is used to handle incoming events.
     */
    public RSBConnection(AbstractEventHandler handler) {
        super(handler);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws InitializationException {@inheritDoc}
     */
    @Override
    protected RSBListener getInitializedListener() throws InitializationException {
        try {
            Scope inScope = JPService.getProperty(JPPSCBaseScope.class).getValue().concat(JPService.getProperty(JPRayScope.class).getValue());
            LOGGER.info("Initializing RSB Listener on scope: " + inScope);
            if (JPService.getProperty(JPLocalInput.class).getValue()) {
                return RSBFactoryImpl.getInstance().createSynchronizedListener(inScope, getLocalConfig());
            } else {
                return RSBFactoryImpl.getInstance().createSynchronizedListener(inScope);
            }
        } catch (CouldNotPerformException | JPNotAvailableException ex) {
            throw new InitializationException(RSBConnection.class, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws InitializationException {@inheritDoc}
     */
    @Override
    protected RSBInformer<UnitProbabilityCollection> getInitializedInformer() throws InitializationException {
        try {
            Scope outScope = JPService.getProperty(JPPSCBaseScope.class).getValue().concat(JPService.getProperty(JPIntentScope.class).getValue());
            LOGGER.info("Initializing RSB Informer on scope: " + outScope);
            if (JPService.getProperty(JPLocalOutput.class).getValue()) {
                LOGGER.warn("RSB output set to socket and localhost.");
                return RSBFactoryImpl.getInstance().createSynchronizedInformer(outScope, UnitProbabilityCollection.class, getLocalConfig());
            } else {
                return RSBFactoryImpl.getInstance().createSynchronizedInformer(outScope, UnitProbabilityCollection.class);
            }
        } catch (JPNotAvailableException | CouldNotPerformException ex) {
            throw new InitializationException(RSBConnection.class, ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void registerConverters() {
        LOGGER.debug("Registering PointingRay3DFloatCollection converter for Listener.");
        registerConverterForType(PointingRay3DFloatDistributionCollection.getDefaultInstance());
        LOGGER.debug("Registering UnitProbabilityCollection converter for Informer.");
        registerConverterForType(UnitProbabilityCollection.getDefaultInstance());
    }
}
