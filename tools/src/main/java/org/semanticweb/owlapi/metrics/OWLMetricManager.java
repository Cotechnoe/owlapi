/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.metrics;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntology;

/** @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group
 * @since 2.1.0 */
public class OWLMetricManager {
    /** The metrics. */
    private final List<OWLMetric<?>> metrics;

    /** Instantiates a new oWL metric manager.
     * 
     * @param metrics
     *            the metrics to use */
    public OWLMetricManager(@Nonnull List<OWLMetric<?>> metrics) {
        this.metrics = new ArrayList<OWLMetric<?>>(checkNotNull(metrics,
                "metrics cannot be null"));
    }

    /** Sets the ontology.
     * 
     * @param ontology
     *            the ontology to measure */
    public void setOntology(@Nonnull OWLOntology ontology) {
        for (OWLMetric<?> metric : metrics) {
            metric.setOntology(ontology);
        }
    }

    /** Gets the metrics.
     * 
     * @return the metrics */
    @Nonnull
    public List<OWLMetric<?>> getMetrics() {
        return new ArrayList<OWLMetric<?>>(metrics);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OWLMetric<?> m : metrics) {
            sb.append(m);
            sb.append("\n");
        }
        return sb.toString();
    }
}
