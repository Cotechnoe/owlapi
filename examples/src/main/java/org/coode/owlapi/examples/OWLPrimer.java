package org.coode.owlapi.examples;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.vocab.XSDVocabulary;
import org.semanticweb.owl.vocab.OWLRestrictedDataRangeFacetVocabulary;
import static org.semanticweb.owl.vocab.OWLRestrictedDataRangeFacetVocabulary.*;
import org.semanticweb.owl.io.StreamOutputTarget;
import org.semanticweb.owl.io.OWLXMLOntologyFormat;
import org.coode.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owl.rdf.turtle.TurtleOntologyFormat;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Apr-2008<br><br>
 *
 * The following example uses entities and axioms that are used in the OWL Primer.
 * The purpose of this example is to illustrate some of the methods of creating class
 * descriptions and various types of axioms.  Typically, an ontology wouldn't be constructed
 * programmatically in a long drawn out fashion like this, it would be constructe in
 * an ontology editor such as Protege 4, or Swoop.  The OWL API would then be used to
 * examine the asserted structure of the ontology, and in conjunction with an OWL reasoner
 * such as FaCT++ or Pellet used to query the inferred ontology.
 */
public class OWLPrimer {

    public static void main(String[] args) {

        try {
            // The OWLOntologyManager is at the heart of the OWL API, we can create
            // an instance of this using the OWLManager class, which will set up commonly
            // used options (such as which parsers are registered etc. etc.)
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // We want to create an ontology that corresponds to the ontology used in the OWL
            // Primer.  Every ontology has a URI that uniquely identifies the ontology.  The
            // URI is essentially a name for the ontology.  Note that the URI doesn't necessarily
            // point to a location on the web - in this example, we won't publish the ontology at
            // the URL corresponding to the ontology URI below.
            URI ontologyURI = URI.create("http://example.com/owl/families");

            // Now that we have a URI for out ontology, we can create the actual ontology.
            // Note that the create ontology method throws an OWLOntologyCreationException if
            // there was a problem creating the ontology.
            OWLOntology ont = manager.createOntology(ontologyURI);

            // We can use the manager to get a reference to an OWLDataFactory.  The data factory
            // provides a point for creating OWL API objects such as classes, properties and
            // individuals.
            OWLDataFactory factory = manager.getOWLDataFactory();

            // We first need to create some references to individuals.  All of our individual must
            // have URIs.  A common convention is to take the URI of an ontology, append a # and then
            // a local name.  For example we can create the individual 'John', using the ontology URI
            // and appending #John.  Note however, that there is no reuqirement that a URI of a class,
            // property or individual that is used in an ontology have a correspondance with the
            // URI of the ontology.
            OWLIndividual john = factory.getOWLIndividual(URI.create(ontologyURI + "#John"));
            OWLIndividual mary = factory.getOWLIndividual(URI.create(ontologyURI + "#Mary"));
            OWLIndividual susan = factory.getOWLIndividual(URI.create(ontologyURI + "#Susan"));
            OWLIndividual bill = factory.getOWLIndividual(URI.create(ontologyURI + "#Bill"));

            // The ontologies that we created aren't contained in any ontology at the moment.
            // Individuals (or classes or properties) can't directly be added to an ontology,
            // they have to be used in axioms, and then the axioms are added to an ontology.

            // We now want to add some facts to the ontology.  These facts are otherwise known
            // as property assertions.

            // In our case, we want to say that John has a wife Mary.  To do this we need to
            // have a reference to the hasWife object property (object properties link an individual
            // to an individual, and data properties link and individual to a constant - here, we
            // need an object property because John and Mary are individuals).
            OWLObjectProperty hasWife = factory.getOWLObjectProperty(URI.create(ontologyURI + "#hasWife"));

            // Now we need to create the assertion that John hasWife Mary. To do this we need
            // an axiom, in this case an object property assertion axiom. This can be thought of
            // as a "triple" that has a subject, john, a predicate, hasWife and an object Mary
            OWLObjectPropertyAssertionAxiom axiom1 = factory.getOWLObjectPropertyAssertionAxiom(john, hasWife, mary);

            // We now need to add this assertion to our ontology.  To do this, we apply an ontology change
            // to the ontology via the OWLOntologyManager.

            // First we create the change object that will tell the manager that we want to add the
            // axiom to the ontology
            AddAxiom addAxiom1 = new AddAxiom(ont, axiom1);

            // Now we apply the change using the manager.
            manager.applyChange(addAxiom1);

            // Now we want to add the other facts/assertions to the ontology
            //
            // John  hasSon Bill
            // Get a refernece to the hasSon property
            OWLObjectProperty hasSon = factory.getOWLObjectProperty(URI.create(ontologyURI + "#hasSon"));
            // Create the assertion,  John hasSon Bill
            OWLAxiom axiom2 = factory.getOWLObjectPropertyAssertionAxiom(john, hasSon, bill);
            // Apply the change
            manager.applyChange(new AddAxiom(ont, axiom2));

            // John hasDaughter Susan
            OWLObjectProperty hasDaughter = factory.getOWLObjectProperty(URI.create(ontologyURI + "#hasDaughter"));
            OWLAxiom axiom3 = factory.getOWLObjectPropertyAssertionAxiom(john, hasDaughter, susan);
            manager.applyChange(new AddAxiom(ont, axiom3));

            // John hasAge 33
            // In this case, hasAge is a data property, which we need a reference to
            OWLDataProperty hasAge = factory.getOWLDataProperty(URI.create(ontologyURI + "#hasAge"));
            // We create a data property assertion instead of an object property assertion
            OWLAxiom axiom4 = factory.getOWLDataPropertyAssertionAxiom(john, hasAge, 33);
            manager.applyChange(new AddAxiom(ont, axiom4));

            // In the above code, 33 is an integer, so we can just pass 33 into the data factory method.
            // Behind the scenes the OWL API will create a typed constant that it will use as the value
            // of the data property assertion.  We could have manually created the constant as follows:
            OWLDataType intDataType = factory.getOWLDataType(XSDVocabulary.INT.getURI());
            OWLTypedConstant thirtyThree = factory.getOWLTypedConstant("33", intDataType);
            // We would then create the axiom as follows:
            factory.getOWLDataPropertyAssertionAxiom(john, hasAge, thirtyThree);
            // However, the convenice method is much shorter!

            // We can now create the other facts/assertion for Mary.  The OWL API uses a change
            // object model, which means we can stack up changes (or sets of axioms) and apply the
            // changes (or add the axioms) in one go.  We will do this for Mary
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            axioms.add(factory.getOWLObjectPropertyAssertionAxiom(mary, hasSon, bill));
            axioms.add(factory.getOWLObjectPropertyAssertionAxiom(mary, hasDaughter, susan));
            axioms.add(factory.getOWLDataPropertyAssertionAxiom(mary, hasAge, 31));

            // Add facts/assertions for Bill and Susan
            axioms.add(factory.getOWLDataPropertyAssertionAxiom(bill, hasAge, 13));
            axioms.add(factory.getOWLDataPropertyAssertionAxiom(mary, hasAge, 8));

            // Now add all the axioms in one go - there is a convenience method on OWLOntologyManager
            // that will automatically generate the AddAxiom change objects for us.  We need to
            // specify the ontology that the axioms should be added to and the axioms to add.
            manager.addAxioms(ont, axioms);

            // Now specify the genders of John, Mary, Bill and Susan.  To do this we need the male
            // and female individuals and the hasGender object property.
            OWLIndividual male = factory.getOWLIndividual(URI.create(ontologyURI + "#male"));
            OWLIndividual female = factory.getOWLIndividual(URI.create(ontologyURI + "#female"));
            OWLObjectProperty hasGender = factory.getOWLObjectProperty(URI.create(ontologyURI + "#hasGender"));

            Set<OWLAxiom> genders = new HashSet<OWLAxiom>();
            genders.add(factory.getOWLObjectPropertyAssertionAxiom(john, hasGender, male));
            genders.add(factory.getOWLObjectPropertyAssertionAxiom(mary, hasGender, female));
            genders.add(factory.getOWLObjectPropertyAssertionAxiom(bill, hasGender, male));
            genders.add(factory.getOWLObjectPropertyAssertionAxiom(susan, hasGender, female));

            // Add the facts about the genders
            manager.addAxioms(ont, genders);


            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Domain and Range Axioms
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // At this point, we have an ontology containing facts about several individuals.
            // We now want to specify more information about the various properties that we have
            // used.  We want to say that the domains and ranges of hasWife, hasSon and hasDaughter
            // are the class Person.  To do this we need various domain and range axioms, and we
            // need a reference to the class Person


            // First get a reference to the person class
            OWLClass person = factory.getOWLClass(URI.create(ontologyURI + "#Person"));
            // Now we add the domain and range axioms that specify the domains and ranges
            // of the various properties that we are interested in.
            Set<OWLAxiom> domainsAndRanges = new HashSet<OWLAxiom>();
            // Domain and then range of hasWife
            domainsAndRanges.add(factory.getOWLObjectPropertyDomainAxiom(hasWife, person));
            domainsAndRanges.add(factory.getOWLObjectPropertyRangeAxiom(hasWife, person));
            // Domain and range of hasSon and also hasDaugher
            domainsAndRanges.add(factory.getOWLObjectPropertyDomainAxiom(hasSon, person));
            domainsAndRanges.add(factory.getOWLObjectPropertyRangeAxiom(hasSon, person));
            domainsAndRanges.add(factory.getOWLObjectPropertyDomainAxiom(hasDaughter, person));
            domainsAndRanges.add(factory.getOWLObjectPropertyRangeAxiom(hasDaughter, person));

            // We also have the domain of the data property hasAge as Person, and the range as
            // integer.
            // We need the integer datatype.  The XML Schema Datatype URIs are used for data types.
            // The OWL API provide a built in set via the XSDVocabulary enum.
            domainsAndRanges.add(factory.getOWLDataPropertyDomainAxiom(hasAge, person));
            OWLDataType integerDataType = factory.getOWLDataType(XSDVocabulary.INTEGER.getURI());
            domainsAndRanges.add(factory.getOWLDataPropertyRangeAxiom(hasAge, integerDataType));

            // Now add all of our domain and range axioms
            manager.addAxioms(ont, domainsAndRanges);


            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Class assertion axioms
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // We can also explicitly say than an individual is an instance of a given class.
            // To do this we use a Class assertion axiom.
            OWLClassAssertionAxiom classAssertionAx = factory.getOWLClassAssertionAxiom(john, person);
            // Add the axiom directly using the addAxiom convenience method on OWLOntologyManager
            manager.addAxiom(ont, classAssertionAx);


            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Inverse property axioms
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // We can specify the inverse property of hasWife as hasHusband
            // We first need a reference to the hasHusband property.
            OWLObjectProperty hasHusband = factory.getOWLObjectProperty(URI.create(ont.getURI() + "#hasHusband"));
            // The full URI of the hasHusband property will be http://example.com/owl/families#hasHusband
            // since the URI of our ontology is http://example.com/owl/families
            // Create the inverse object properties axiom and add it
            manager.addAxiom(ont, factory.getOWLInverseObjectPropertiesAxiom(hasWife, hasHusband));


            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Sub property axioms
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // OWL allows a property hierarchy to be specified.  Here, hasSon and hasDaughter will
            // be specified as hasChild.
            OWLObjectProperty hasChild = factory.getOWLObjectProperty(URI.create(ont.getURI() +  "#hasChild"));
            OWLObjectSubPropertyAxiom hasSonSubHasChildAx = factory.getOWLSubObjectPropertyAxiom(hasSon, hasChild);
            // Add the axiom
            manager.addAxiom(ont, hasSonSubHasChildAx);
            // And hasDaughter, which is also a sub property of hasChild
            manager.addAxiom(ont, factory.getOWLSubObjectPropertyAxiom(hasDaughter, hasChild));


            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Property characteristics
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // Next, we want to say that the hasAge property is Functional.  This means that
            // something can have at most one hasAge property.  We can do this with a
            // functional data property axiom
            // First create the axiom
            OWLFunctionalDataPropertyAxiom hasAgeFuncAx = factory.getOWLFunctionalDataPropertyAxiom(hasAge);
            // Now add it to the ontology
            manager.addAxiom(ont, hasAgeFuncAx);

            // The hasWife property should be Functional, InverseFunctional, Irreflexive and Asymmetric.
            // Note that the asymmetric property axiom used to be called antisymmetric - older versions
            // of the OWL API may refer to antisymmetric property axioms
            Set<OWLAxiom> hasWifeAxioms = new HashSet<OWLAxiom>();
            hasWifeAxioms.add(factory.getOWLFunctionalObjectPropertyAxiom(hasWife));
            hasWifeAxioms.add(factory.getOWLInverseFunctionalObjectPropertyAxiom(hasWife));
            hasWifeAxioms.add(factory.getOWLIrreflexiveObjectPropertyAxiom(hasWife));
            hasWifeAxioms.add(factory.getOWLAntiSymmetricObjectPropertyAxiom(hasWife));
            // Add all of the axioms that specify the characteristics of hasWife
            manager.addAxioms(ont, hasWifeAxioms);

            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  SubClass axioms
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // Now we want to start specifying something about classes in our ontology.  To begin with
            // we will simply say something about the relationship between named classes
            // Besides the Person class that we already have, we want to say something about the classes
            // Man, Woman and Parent.  To say something about these classes, as usual, we need references
            // to them:

            OWLClass man = factory.getOWLClass(URI.create(ontologyURI + "#Man"));
            OWLClass woman = factory.getOWLClass(URI.create(ontologyURI + "#Woman"));
            OWLClass parent = factory.getOWLClass(URI.create(ontologyURI + "#Parent"));

            // It is important to realise that simply getting references to a class via the data factory
            // does not add them to an ontology - only axioms can be added to an ontology.

            // Now say that Man, Woman and Parent are subclasses of Person
            manager.addAxiom(ont, factory.getOWLSubClassAxiom(man, person));
            manager.addAxiom(ont, factory.getOWLSubClassAxiom(woman, person));
            manager.addAxiom(ont, factory.getOWLSubClassAxiom(parent, person));

            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Restrictions
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // Now we want to say that Person has exactly 1 Age, exactly 1 Gender and,  only has gender
            // that is male or female.  We will deal with these restrictions one by one and then combine
            // them as a superclass (Necessary conditions) of Person.  All anonymous class descriptions
            // extend OWLDescription.

            // First, hasAge exactly 1
            OWLDataExactCardinalityRestriction hasAgeRestriction = factory.getOWLDataExactCardinalityRestriction(hasAge, 1);
            // Now the hasGender exactly 1
            OWLObjectExactCardinalityRestriction hasGenderRestriction = factory.getOWLObjectExactCardinalityRestriction(hasGender, 1);
            // And finally, the hasGender only {male female}
            // To create this restriction, we need an OWLObjectOneOf description since male and female are individuals
            // We can just list as many individuals as we need as the argument of the method.
            OWLObjectOneOf maleOrFemale = factory.getOWLObjectOneOf(male, female);
            // Now create the actual restriction
            OWLObjectAllRestriction hasGenderOnlyMaleFemale = factory.getOWLObjectAllRestriction(hasGender, maleOrFemale);

            // Finally, we bundle these restrictions up into an intersection, since we want person
            // to be a subclass of the intersection of them
            OWLObjectIntersectionOf intersection = factory.getOWLObjectIntersectionOf(hasAgeRestriction,
                                                                                      hasGenderRestriction,
                                                                                      hasGenderOnlyMaleFemale);
            // And now we set this anonymous intersection class to be a superclass of Person using a subclass axiom
            manager.addAxiom(ont, factory.getOWLSubClassAxiom(person, intersection));


            // Restrictions and other anonymous classes can also be used anywhere a named class can be used.
            // Let's set the range of hasSon to be Person and hasGender value male.  This requires an anonymous
            // class that is the intersection of Person, and also, hasGender value male.  We need to create
            // the hasGender value male restriction - this describes the class of things that have a hasGender
            // relationship to the individual male.
            OWLObjectValueRestriction hasGenderValueMaleRestriction = factory.getOWLObjectValueRestriction(hasGender,
                                                                                                           male);
            // Now combine this with Person in an intersection
            OWLDescription personAndHasGenderValueMale = factory.getOWLObjectIntersectionOf(person,
                                                                                       hasGenderValueMaleRestriction);
            // Now specify this anonymous class as the range of hasSon using an object property range axioms
            manager.addAxiom(ont, factory.getOWLObjectPropertyRangeAxiom(hasSon, personAndHasGenderValueMale));


            // We can do a similar thing for hasDaughter, by specifying that hasDaughter has a range
            // of Person and hasGender value female.  This time, we will make things a little more compact by
            // not using so many variables

            OWLDescription rangeOfHasDaughter = factory.getOWLObjectIntersectionOf(person,
                                                                                factory.getOWLObjectValueRestriction(hasGender,
                                                                                                                     female));
            manager.addAxiom(ont, factory.getOWLObjectPropertyRangeAxiom(hasDaughter, rangeOfHasDaughter));



            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Data Ranges and Equivalent Classes axioms
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // In OWL 2, we can specify expressive data ranges.  Here, we will specify the classes
            // Teenage, Adult and Child by saying something about individuals ages.

            // First we take the class Teenager, all of whose instance have an age greater or equal to
            // 13 and less than 20.  In Manchester Syntax this is written as Person and hasAge some int[>=13, <20]
            // We create a data range by taking the integer datatype and applying facet restrictions to it.
            // Note that we have statically imported the data range facet vocabulary OWLRestrictedDataRangeFacetVocabulary
            OWLDataRangeFacetRestriction geq13 = factory.getOWLDataRangeFacetRestriction(MIN_INCLUSIVE, factory.getOWLTypedConstant(13));
            // We don't have to explicitly create the typed constant, there are convenience methods to do this
            OWLDataRangeFacetRestriction lt20 = factory.getOWLDataRangeFacetRestriction(MAX_EXCLUSIVE, 20);
            // Restrict the base type, integer (which is just an XML Schema Datatype) with the facet
            // restrictions.
            OWLDataRange dataRng = factory.getOWLDataRangeRestriction(integerDataType, geq13, lt20);
            // Now we have the data range of greater than equal to 13 and less than 20 we can use this in a
            // restriction.
            OWLDataSomeRestriction teenagerAgeRestriction = factory.getOWLDataSomeRestriction(hasAge, dataRng);

            // Now make Teenager equivalent to Person and hasAge some int[>=13, <20]
            // First create the class Person and hasAge some int[>=13, <20]
            OWLDescription teenagePerson = factory.getOWLObjectIntersectionOf(person, teenagerAgeRestriction);

            OWLClass teenager = factory.getOWLClass(URI.create(ontologyURI + "#Teenager"));
            OWLEquivalentClassesAxiom teenagerDefinition = factory.getOWLEquivalentClassesAxiom(teenager, teenagePerson);
            manager.addAxiom(ont, teenagerDefinition);


            // Do the same for Adult that has an age greater than 21
            OWLDataRange geq21 = factory.getOWLDataRangeRestriction(integerDataType,
                                                                    factory.getOWLDataRangeFacetRestriction(MIN_INCLUSIVE, 21));
            OWLClass adult = factory.getOWLClass(URI.create(ontologyURI + "#Adult"));
            OWLDescription adultAgeRestriction = factory.getOWLDataSomeRestriction(hasAge, geq21);
            OWLDescription adultPerson = factory.getOWLObjectIntersectionOf(person, adultAgeRestriction);
            OWLAxiom adultDefinition = factory.getOWLEquivalentClassesAxiom(adult, adultPerson);
            manager.addAxiom(ont, adultDefinition);

            // And finally Child
            OWLDataRange notGeq21 = factory.getOWLDataComplementOf(geq21);
            OWLClass child = factory.getOWLClass(URI.create(ontologyURI + "#Child"));
            OWLDescription childAgeRestriction = factory.getOWLDataSomeRestriction(hasAge, notGeq21);
            OWLDescription childPerson = factory.getOWLObjectIntersectionOf(person, childAgeRestriction);
            OWLAxiom childDefinition = factory.getOWLEquivalentClassesAxiom(child, childPerson);
            manager.addAxiom(ont, childDefinition);


            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Different individuals
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // In OWL, we can say that individuals are different from each other.  To do this we use a
            // different individuals axiom.  Since John, Mary, Bill and Susan are all different individuals,
            // we can express this using a different individuals axiom.
            OWLDifferentIndividualsAxiom diffInds = factory.getOWLDifferentIndividualsAxiom(john, mary, bill, susan);
            manager.addAxiom(ont, diffInds);
            // Male and Female are also different
            manager.addAxiom(ont, factory.getOWLDifferentIndividualsAxiom(male, female));

            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Disjoint classes
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // Two say that two classes do not have any instances in common we use a disjoint classes
            // axiom:
            OWLDisjointClassesAxiom disjointClasses = factory.getOWLDisjointClassesAxiom(man, woman);
            manager.addAxiom(ont, disjointClasses);

            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //  Ontology Management
            //
            //////////////////////////////////////////////////////////////////////////////////////////////

            // Having added axioms to out ontology we can now save it (in a variety of formats).

            // RDF/XML is the default format
            System.out.println("RDF/XML: -------------------------------------------------------------------------------");
            manager.saveOntology(ont, new StreamOutputTarget(System.out));

            // OWL/XML
            System.out.println("OWL/XML: -------------------------------------------------------------------------------");
            manager.saveOntology(ont, new OWLXMLOntologyFormat(), new StreamOutputTarget(System.out));


            // Manchester Syntax
            System.out.println("Manchester syntax: -------------------------------------------------------------------------------");
            manager.saveOntology(ont, new ManchesterOWLSyntaxOntologyFormat(), new StreamOutputTarget(System.out));

            // Turtle
            System.out.println("Turtle: -------------------------------------------------------------------------------");
            manager.saveOntology(ont, new TurtleOntologyFormat(), new StreamOutputTarget(System.out));



        }
        catch (OWLOntologyCreationException e) {
            System.out.println("There was a problem creating the ontology: " + e.getMessage());
        }
        catch (OWLOntologyChangeException e) {
            System.out.println("Could not apply change to ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Problem saving ontology: " + e.getMessage());
        }
    }

}
