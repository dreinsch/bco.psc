package org.openbase.bco.psc.util.jp;

/*
 * -
 * #%L
 * BCO PSC Utility
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import java.util.List;
import org.openbase.jps.exception.JPBadArgumentException;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.preset.AbstractJPString;

/**
 * JavaProperty representing the unit id of the Kinect device config to be updated.
 *
 * @author <a href="mailto:thuppke@techfak.uni-bielefeld.de">Thoren Huppke</a>
 */
public class JPKinectUnitId extends AbstractJPString {

    /**
     * The identifiers that can be used in front of the command line argument.
     */
    public final static String[] COMMAND_IDENTIFIERS = {"-u", "--unit-id"};

    private final static String HEXA_REGEX = "[0-9a-fA-F]";
    private final static String UNIT_ID_REGEX = HEXA_REGEX + "{8}-" + HEXA_REGEX + "{4}-" + HEXA_REGEX + "{4}-" + HEXA_REGEX + "{4}-" + HEXA_REGEX + "{12}";

    /**
     * Constructor.
     */
    public JPKinectUnitId() {
        super(COMMAND_IDENTIFIERS);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws JPNotAvailableException {@inheritDoc}
     */
    @Override
    protected String getPropertyDefaultValue() throws JPNotAvailableException {
        return "";
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "The unit id of the existing Kinect device config.";
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments {@inheritDoc}
     * @return {@inheritDoc}
     * @throws JPBadArgumentException {@inheritDoc}
     */
    @Override
    protected String parse(List<String> arguments) throws JPBadArgumentException {
        String result = super.parse(arguments);
        if (!result.matches(UNIT_ID_REGEX)) {
            throw new JPBadArgumentException("The given unit id " + result + " is not valid.");
        }
        return result;
    }
}
