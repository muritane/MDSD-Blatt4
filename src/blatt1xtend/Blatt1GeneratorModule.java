package blatt1xtend;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;
 
public class Blatt1GeneratorModule extends AbstractGenericResourceRuntimeModule {
 
    @Override
    protected String getLanguageName() {
        return "Blatt1 Model Editor";
    }
 
    @Override
    protected String getFileExtensions() {
//        return "repository";
        return "types";
    }
 
    public Class<? extends AbstractGenerator> bindIGenerator() {
        return Blatt1Generator.class;
    }
 
    public Class<? extends ResourceSet> bindResourceSet() {
        return ResourceSetImpl.class;
    }
 
}
