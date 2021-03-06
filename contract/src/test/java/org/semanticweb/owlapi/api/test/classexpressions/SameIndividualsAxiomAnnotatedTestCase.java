package org.semanticweb.owlapi.api.test.classexpressions;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

import java.util.Arrays;

import org.semanticweb.owlapi.api.test.baseclasses.AnnotatedAxiomRoundTrippingTestCase;

public class SameIndividualsAxiomAnnotatedTestCase extends AnnotatedAxiomRoundTrippingTestCase {
    public SameIndividualsAxiomAnnotatedTestCase() {
        super(a -> df.getOWLSameIndividualAxiom(
            Arrays.asList(NamedIndividual(iri("A")), NamedIndividual(iri("B"))), a));
    }
}
