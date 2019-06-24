package blatt1xtend

import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.emf.ecore.EObject
import blatt1.Repository.Component
import blatt1.Repository.Types.Signature
import blatt1.Repository.Types.Void
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import com.google.inject.Inject
import blatt1.Repository.Interface

//class Blatt1Generator extends AbstractGenerator {
class Blatt1Generator implements IGenerator {
 
    @Inject extension IQualifiedNameProvider
    
    override void doGenerate(Resource resource, IFileSystemAccess fsa) {
//        for (EObject o : resource.contents) {
        for (o : resource.allContents.toIterable.filter(Void)) {
        	fsa.generateFile(
//        		o.fullyQualifiedName.toString("/") + ".java",
				"void.java",
        		o.compile)
        }
    }
 
//    override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
//        println("hier")
//        for (e : resource.allContents.toIterable.filter(Void)) {
//            fsa.generateFile(
//                e.fullyQualifiedName.toString("/") + ".java",
//                e.compile)
//        }
//    }
 
//    def dispatch compile(Interface e) ''' 
//        «IF e.eContainer.fullyQualifiedName !== null»
//            package «e.eContainer.fullyQualifiedName»;
//        «ENDIF»
//        
//        public class «e.name» 
//        {
//            «FOR f : e.signature»
//                «f.compile»
//            «ENDFOR»
//        }
//    '''
 
    def dispatch compile(Signature s) '''
    	
    '''
    
    def compile(Void m) '''
    	System.out.println("void");
    '''
    
    def compile(Component c) '''
    	System.out.println("Component");
    '''
    
//    def dispatch void compile(Void m, IFileSystemAccess fsa) {
//    	fsa.generateFile("void.txt", '''
//		        this is element «"void"»
//		        ''')
//    }
    
    def dispatch void compile(Signature m, IFileSystemAccess fsa) {
    	m.returnType.compile(fsa);
    	for (EObject o : m.parameterType) {
            o.compile(fsa)
        }
        fsa.generateFile(m.name+".txt", '''
		        this is element «m.name»
		        ''')
    }
 
    def dispatch void compile(Component m, IFileSystemAccess fsa) {
		m.behaviourDescription.compile(fsa);
		for (EObject o : m.providedInterface) {
            o.compile(fsa)
        }
		for (EObject o : m.providedService) {
            o.compile(fsa)
        }
		for (EObject o : m.requiredInterface) {
            o.compile(fsa)
        }
		for (EObject o : m.requiredService) {
            o.compile(fsa)
        }
		fsa.generateFile(m.name+".txt", '''
		        this is element «m.name»
		        ''')
    }
 
    def dispatch void compile(EObject m, IFileSystemAccess fsa) { }
 
}
