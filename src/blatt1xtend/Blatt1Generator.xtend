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
import blatt1.Repository.Types.Type

class Blatt1Generator extends AbstractGenerator {
 
//    override void doGenerate(Resource resource, IFileSystemAccess fsa) {
//        for (EObject o : resource.contents) {
//            o.compile(fsa)
//        }
//    }
    
    @Inject extension IQualifiedNameProvider
 
    override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
        for (e : resource.allContents.toIterable.filter(Interface)) {
            fsa.generateFile(
                e.fullyQualifiedName.toString("/") + ".java",
                e.compile)
        }
    }
 
    def compile(Interface e) ''' 
        «IF e.eContainer.fullyQualifiedName !== null»
            package «e.eContainer.fullyQualifiedName»;
        «ENDIF»
        
        public class «e.name» {
            «FOR f : e.signature»
                «f.compile»
            «ENDFOR»
        }
    '''
 
    def compile(Signature s) '''
    	
    '''
    
    def compile(Type t) '''
    	
    '''
    
//    def dispatch void compile(EObject m, IFileSystemAccess fsa) { }
 
}
