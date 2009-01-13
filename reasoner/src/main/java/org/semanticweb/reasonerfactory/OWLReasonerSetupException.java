package org.semanticweb.reasonerfactory;

import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.model.OWLRuntimeException;
/*
 * Copyright (C) 2008, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 08-Sep-2008<br><br>
 */
public class OWLReasonerSetupException extends OWLRuntimeException {

    public OWLReasonerSetupException(OWLReasonerFactory factory, Throwable cause) {
        super(getErrorMessage(factory), cause);
    }

    private static String getErrorMessage(OWLReasonerFactory factory) {
        return "There was a problem setting up the reasoner (" + factory.getReasonerName() +
        ").  Please ensure that you have the appropriate classes (jar files) are in your class path";
    }
}
