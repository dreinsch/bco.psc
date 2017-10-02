package org.openbase.bco.psc.sm.merging;

/*-
 * #%L
 * BCO PSC Skeleton Merging
 * %%
 * Copyright (C) 2016 - 2017 openbase.org
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

import javafx.geometry.Point3D;
import org.openbase.bco.psc.lib.pointing.PostureFunctions;
import rst.geometry.TranslationType.Translation;

/**
 *
 * @author <a href="mailto:thuppke@techfak.uni-bielefeld.de">Thoren Huppke</a>
 */
public class Joint3D {

    private final Translation translation;
    private final Point3D position;
    private final float confidence;

    public Joint3D(Point3D position, float confidence) {
        this.position = position;
        this.translation = PostureFunctions.toTranslation(position);
        this.confidence = confidence;
    }

    public Joint3D(Translation translation, float confidence) {
        this.translation = translation;
        this.position = PostureFunctions.toPoint3D(translation);
        this.confidence = confidence;
    }

    public Translation getTranslation() {
        return translation;
    }

    public Point3D getPosition() {
        return position;
    }

    public float getConfidence() {
        return confidence;
    }

}
