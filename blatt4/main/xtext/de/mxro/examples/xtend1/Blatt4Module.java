package de.mxro.examples.xtend1
 
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;
 
public class Blatt4Module extends AbstractGenericResourceRuntimeModule {
 
    @Override
    protected String getLanguageName() {
        return "sample.presentation.SampleEditorID";
    }
 
    @Override
    protected String getFileExtensions() {
        return "sample";
    }
 
    public Class<? extends IGenerator> bindIGenerator() {
        return SampleGenerator.class;
    }
 
    public Class<? extends ResourceSet> bindResourceSet() {
        return ResourceSetImpl.class;
    }
 
}
