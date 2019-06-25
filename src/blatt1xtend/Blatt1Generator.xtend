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
        for (o : resource.allContents.toIterable.filter(Interface)) { 
        //Signaturen & Parameter/Return werte sollten automatisch folgen, da deren Kompilierung "rekursiv" aufgerufen wid.
        	fsa.generateFile(
        		o.fullyQualifiedName.toString("/") + ".java",
				//"RemoveLater.java",
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
        
        public interface «e.name» 
        {
            «FOR f : e.signature»
                «f.compile»
            «ENDFOR»
        }
    '''
 
    def compile(Signature s) '''
	public «s.returnType.compile» «s.name»(«FOR p : s.parameterType»«IF !p.toString().contains("Void")»«p.compile»«ENDIF»«ENDFOR») {
		// TODO: Insert code here
	}
	'''
    
    def compile(Type m) '''«IF m.toString().contains("Void")»void«ENDIF»'''
    
    
    def compile(Void m) '''
    	voidS
    '''
    
    def compile(Component c) '''
    	«IF c.eContainer.fullyQualifiedName !== null»
	        package «c.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class «c.name» implements
    	«FOR pi : c.providedInterface» //loop for all implemented Interfaces
    		«pi.name»
			«IF pi != c.providedInterface.last»
			,
			«ENDIF»
		«ENDFOR»
    	{
    		«««//überflüssig: wurde zuvor schon generiert - stattdessen methodenrümpfe für interfaces generieren
			«««»»«FOR pi : c.providedInterface»
			«««»	«pi.compile»
			«««»«ENDFOR
			«««»»«FOR ri : c.requiredInterface»
			«««»	«ri.compile»
			«««»«ENDFOR»
			«FOR pi : c.providedInterface» «««TODO WARNING was für provided, was für required? siehe Aufgabe 4 B
				«««»»//«pi.name»
				«FOR s : pi.signature»
					//«pi.name»
					@Override
					public «s.returnType» «s.name» (
					«FOR p : s.parameterType» //loop for all parameters of the method
			    		«««»«p.name» TODO catch this somehow, also define a unique name
			    		«««»«s.parameterType.index(p)» irgend sowas für eindeutigen namen für parameter?
						«IF p != s.parameterType.last»
						,
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
			«ENDFOR»
			«FOR ri : c.requiredInterface»
				//«ri.name»
				«FOR s : ri.signature»
					«s.returnType» «s.name»;
					public set«s.name» ( «s.returnType» «s.name»){ this.«s.name» = «s.name»;}
				«ENDFOR»
			«ENDFOR»
			«FOR ps : c.providedService»
				«ps.compile»
			«ENDFOR»
			«FOR rs : c.requiredService»
				«rs.compile»
			«ENDFOR»
			«c.behaviourDescription.compile»
    	}
    	System.out.println("Component");
    '''
    
    def compile(Service s) '''
		«IF s.eContainer.fullyQualifiedName !== null»
            package «s.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class «s.name»
    	{
	    	«FOR sign : s.correspondingSignature»
				«sign.compile»
			«ENDFOR»
	    }
    '''
    
    def compile(BehaviorDescription b)'''
    	«IF b.eContainer.fullyQualifiedName !== null»
            package «b.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class BehaviorDescription //TODO WARNING - hat keinen namen -> kann nicht nach namen benannt werden -> siehe TODO WARNING oben - INSTANZ namen als KLASSEN namen?
    	{
		    «FOR s : b.calledServices»
				«s.compile» 
			«ENDFOR»
			//TODO branch loop etc?
		}
    '''
    
    def compile (System s)'''
    	«IF s.eContainer.fullyQualifiedName !== null»
            package «s.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class System
    	{
    		«FOR ac : s.encapsulatedAssemblyContext»
				«ac.compile»
			«ENDFOR»
			«FOR pi : s.providedInterfaces»
				«pi.compile»
			«ENDFOR»
    	}
    '''
    
    def compile (AssemblyConnector a)'''
		«IF a.eContainer.fullyQualifiedName !== null»
            package «a.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class AssemblyConnector
    	{
    		«a.providedAssemblyContext.compile»
    		«a.requiredAssemblyContext.compile»
    		«a.requiredRole.compile»
    		«a.providedRole.compile»
    	}
    '''
    
    def compile (AssemblyContext a)'''
		«IF a.eContainer.fullyQualifiedName !== null»
            package «a.eContainer.fullyQualifiedName»;
        «ENDIF»
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
	    }
    '''
    
    def compile (Role r)'''
	    «IF r.eContainer.fullyQualifiedName !== null»
	        package «r.eContainer.fullyQualifiedName»;
	    «ENDIF»
    	public class «r.name»
    	{

    	}
    '''
    
    def compile (DelegationConnector d)'''
		«IF d.eContainer.fullyQualifiedName !== null»
            package «d.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class DelegationConnector
    	{
    		«d.role.compile»
    		«d.interface.compile»
    	}
    '''
    
    def compile (Allocation a)'''
	 	«IF a.eContainer.fullyQualifiedName !== null»
            package «a.eContainer.fullyQualifiedName»;
        «ENDIF»
    	 public class Allocation
    	 {
    	 	«a.system.compile»
    	 	«a.environment.compile»
    	 	«FOR ac : a.allocationContexts»
				«ac.compile»
			«ENDFOR»
    	 }
    '''
    
    def compile (AllocationContext a)'''
		«IF a.eContainer.fullyQualifiedName !== null»
            package «a.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class AllocationContext
    	{
    		«FOR tc : a.targetContainer»
				«tc.compile»
			«ENDFOR»
			«FOR ac : a.allocatedAssemblyContext»
				«ac.compile»
			«ENDFOR»
    	}
    '''
    
    def compile (Container c)'''
		«IF c.eContainer.fullyQualifiedName !== null»
			package «c.eContainer.fullyQualifiedName»;
        «ENDIF»
    	public class «c.name»
    	{
    		«FOR l : c.links»
				«l.compile»
			«ENDFOR»
    	}
    '''
    
    def compile (Link l)'''
		«IF l.eContainer.fullyQualifiedName !== null»
            package «l.eContainer.fullyQualifiedName»;
        «ENDIF»
	    public class «l.name»
    	{
    		«FOR c : l.linkedContainers»
				«l.compile»
			«ENDFOR»
    	}
    '''
    
    def compile (Environment e)'''
		«IF e.eContainer.fullyQualifiedName !== null»
            package «e.eContainer.fullyQualifiedName»;
        «ENDIF»
	    public class Environment
    	{
    		«FOR c : e.container»
				«c.compile»
			«ENDFOR»
			«FOR l : e.link»
				«l.compile»
			«ENDFOR»»
    	}
    '''
 
    def dispatch void compile(EObject m, IFileSystemAccess fsa) { }
 
}
