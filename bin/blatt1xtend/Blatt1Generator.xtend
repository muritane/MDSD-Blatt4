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
import blatt1.*
import blatt1.Repository.*
import blatt1.Repository.Types.*
import blatt1.Repository.Behaviour.*
import blatt1.Assembly.*
import blatt1.Allocation.*
import blatt1.Environment.*

//class Blatt1Generator extends AbstractGenerator {
class Blatt1Generator implements IGenerator {
 
    @Inject extension IQualifiedNameProvider
    
    override void doGenerate(Resource resource, IFileSystemAccess fsa) {
//        for (EObject o : resource.contents) {
        for (o : resource.allContents.toIterable.filter(Signature)) {
        	fsa.generateFile(
//        		o.fullyQualifiedName.toString("/") + ".java",
				"RemoveLater.java",
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
 
    def compile(Interface e) ''' //war "dispatch" und auskommentiert - warum?
        «IF e.eContainer.fullyQualifiedName !== null»
            package «e.eContainer.fullyQualifiedName»;
        «ENDIF»
        
        public class «e.name» 
        {
            «FOR f : e.signature»
                «f.compile»
            «ENDFOR»
        }
    '''
 
    def compile(Signature s) '''public class RemoveLater {
	public «s.returnType.compile» «s.name»(«FOR p : s.parameterType»«IF !p.toString().contains("Void")»«p.compile»«ENDIF»«ENDFOR») {
		// TODO: Insert code here
	}
}'''
    
    def compile(Type m) '''«IF m.toString().contains("Void")»void«ENDIF»'''
    
    
    def compile(Void m) '''
    	void
    '''
    
    //helping function for compiling components and composite components
    def compileComponent(Component c) '''
    	«FOR pi : c.providedInterface»
			«pi.compile» //interface compile wieder einführen - warum auskommentiert?
		«ENDFOR»
		«FOR ri : c.requiredInterface»
			«ri.compile» //interface compile wieder einführen - warum auskommentiert?
		«ENDFOR»
		«FOR ps : c.providedService»
			«ps.compile»
		«ENDFOR»
		«FOR rs : c.requiredService»
			«rs.compile»
		«ENDFOR»
		«c.behaviourDescription.compile»
		«IF c.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit c.name?
            package «c.eContainer.fullyQualifiedName»;
        «ENDIF»
    '''
    
    def compile(CompositeComponent c) '''
    	public class «c.name» //TODO WARNING sollte das wirklich so sein? siehe behaviour description: es sollte doch nicht jede INSTANZ eine eige KLASSE haben, oder?
    	{
    		«compileComponent(c)»
    		«FOR a : c.encapsulatedAssemblyContext»
				«a.compile»
			«ENDFOR»
    	}
    '''
    
    def compile(Component c) '''
    	public class «c.name»
    	{
    		«compileComponent(c)»
    	}
    	System.out.println("Component");
    '''
    
    def compile(Service s) '''
    	public class «s.name»
    	{
	    	«FOR sign : s.correspondingSignature»
				«sign.compile»
			«ENDFOR»
			«IF s.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «s.eContainer.fullyQualifiedName»;
	        «ENDIF»
	    }
    '''
    
    def compile(BehaviorDescription b)'''
    	public class BehaviorDescription //TODO WARNING - hat keinen namen -> kann nicht nach namen benannt werden -> siehe TODO WARNING oben - INSTANZ namen als KLASSEN namen?
    	{
		    «FOR s : b.calledServices»
				«s.compile» 
			«ENDFOR»
			//TODO branch loop etc?
		}
    '''
    
    def compile (System s)'''
    	public class System
    	{
    		«FOR ac : s.encapsulatedAssemblyContext»
				«ac.compile»
			«ENDFOR»
			«FOR pi : s.providedInterfaces»
				«pi.compile»
			«ENDFOR»
			«IF s.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «s.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	}
    '''
    
    def compile (AssemblyConnector a)'''
    	public class AssemblyConnector
    	{
    		«a.providedAssemblyContext.compile»
    		«a.requiredAssemblyContext.compile»
    		«a.requiredRole.compile»
    		«a.providedRole.compile»
    		«IF a.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «a.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	}
    '''
    
    def compile (AssemblyContext a)'''
    	public class «a.name»
    	{
	    	«FOR rr : a.requiredRole»
				«rr.compile»
			«ENDFOR»
			«FOR pr : a.providedRole»
				«pr.compile»
			«ENDFOR»
			«a.component.compile»
			«a.allocationContext.compile»
			«IF a.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «a.eContainer.fullyQualifiedName»;
	        «ENDIF»
	    }
    '''
    
    def compile (Role r)'''
    	public class «r.name»
    	{
    		«IF r.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «r.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	}
    '''
    
    def compile (DelegationConnector d)'''
    	public class DelegationConnector
    	{
    		«d.role.compile»
    		«d.interface.compile»
			«IF d.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «d.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	}
    '''
    
    def compile (Allocation a)'''
    	 public class Allocation
    	 {
    	 	«a.system.compile»
    	 	«a.environment.compile»
    	 	«FOR ac : a.allocationContexts»
				«ac.compile»
			«ENDFOR»
    	 	«IF a.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «a.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	 }
    '''
    
    def compile (AllocationContext a)'''
    	public class AllocationContext
    	{
    		«FOR tc : a.targetContainer»
				«tc.compile»
			«ENDFOR»
			«FOR ac : a.allocatedAssemblyContext»
				«ac.compile»
			«ENDFOR»
			«IF a.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «a.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	}
    '''
    
    def compile (Container c)'''
    	public class «c.name»
    	    	{
    	    		«FOR l : c.links»
    					«l.compile»
    				«ENDFOR»
    				«IF c.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
			            package «c.eContainer.fullyQualifiedName»;
			        «ENDIF»
    	    	}
    '''
    
    def compile (Link l)'''
	    public class «l.name»
    	{
    		«FOR c : l.linkedContainers»
				«l.compile»
			«ENDFOR»
			«IF l.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «l.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	}
    '''
    
    def compile (Environment e)'''
	    public class Environment
    	{
    		«FOR c : e.container»
				«c.compile»
			«ENDFOR»
			«FOR l : e.link»
				«l.compile»
			«ENDFOR»
			«IF e.eContainer.fullyQualifiedName !== null» //alles was für den Namen gebraucht wird? was mit s.name?
	            package «e.eContainer.fullyQualifiedName»;
	        «ENDIF»
    	}
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
