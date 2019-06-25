package blatt1xtend

import blatt1.Allocation.Allocation
import blatt1.Allocation.AllocationContext
import blatt1.Assembly.AssemblyConnector
import blatt1.Assembly.AssemblyContext
import blatt1.Assembly.DelegationConnector
import blatt1.Assembly.Role
import blatt1.Assembly.System
import blatt1.Environment.Container
import blatt1.Environment.Environment
import blatt1.Environment.Link
import blatt1.Repository.Behaviour.BehaviorDescription
import blatt1.Repository.Component
import blatt1.Repository.Interface
import blatt1.Repository.Service
import blatt1.Repository.Types.Signature
import blatt1.Repository.Types.Type
import blatt1.Repository.Types.Void
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.emf.common.util.EList

//class Blatt1Generator extends AbstractGenerator {
class Blatt1Generator implements IGenerator {
 
    @Inject extension IQualifiedNameProvider
    
    override void doGenerate(Resource resource, IFileSystemAccess fsa) {
//    	String helper = "Helper".toString("/");
    	fsa.generateFile("repository/Helper.java", helper());
		for (o : resource.allContents.toIterable.filter(Interface)) { 
        	fsa.generateFile(
        		"repository/" + o.fullyQualifiedName.toString("/") + ".java",
        		o.compile)
        }
        for (o : resource.allContents.toIterable.filter(Component)) { 
        	fsa.generateFile(
        		o.name + "/" + o.fullyQualifiedName.toString("/") + ".java",
        		o.compile)
        }
    }
    
    def helper() '''
		package repository;
		
		import org.junit.Assert;
		
		public class Helper {
			public static void assertNotNull(Object o) {
				Assert.assertNotNull(o);
			}
			
			public static void assertNull(Object o) {
				Assert.assertNull(o);
			}
		}
	'''
    
 
    def compile(Interface e) '''
		package repository;
		
		public interface «e.name» 
		{
		    «FOR f : e.signature»
		        «f.compile»
		    «ENDFOR»
		}
	'''
 
    def compile(Signature s) '''
		public «s.returnType.compile» «s.name»(«s.parameterType.compile»);
	'''
	
	def compile(EList<Type> l) '''
		«FOR p : l»
			«IF !p.toString().contains("Void")»
				«p.compile»
			«ENDIF»
			«IF p != l.last»
				,
			«ENDIF»
		«ENDFOR»
	'''
    
	def compile(Type m) '''«IF m.toString().contains("Void")»void«ENDIF»'''
    
    
    def compile(Void m) '''
    	voidS
    '''
    
    def compileForComponent(EList<Interface> l)
    	'''«FOR pi : l»«pi.name»«IF pi != l.last», «ENDIF»«ENDFOR»'''

    def compile(Component c) '''
		package «c.name»;
		
       	«FOR pi : c.providedInterface»
			import repository.«pi.name»;
		«ENDFOR»
       	«FOR ri : c.requiredInterface»
			import repository.«ri.name»;
		«ENDFOR»
		«IF c.requiredInterface.size() !== 0»
			import repository.Helper;
        «ENDIF»
        
		public class «c.name» implements «c.providedInterface.compileForComponent» {
			
			«c.requiredInterface.compileForComponentRequired»
			«c.providedInterface.compileForComponentProvided(c.requiredInterface)»
		}
«««		«FOR ps : c.providedService»
«««				«ps.compile»
«««			«ENDFOR»
«««			
«««			«FOR rs : c.requiredService»
«««				«rs.compile»
«««			«ENDFOR»
«««			«c.behaviourDescription.compile»
    '''
	
	def compileForComponent(Signature s) '''
	public «s.returnType.compile» «s.name»(«s.parameterType.compile») {
		// TODO: Insert code here
	}
	'''
    
    def compileForComponentProvided(EList<Interface> pIfaces, EList<Interface> rIfaces) '''
		«FOR pi : pIfaces» «««TODO WARNING was für provided, was für required? siehe Aufgabe 4 B
			«««»»//«pi.name»
			«FOR s : pi.signature»
				// Implementing «s.name» from interface «pi.name»
				@Override
				public «s.returnType.compile» «s.name»(«s.parameterType.compile») {
					«FOR ri : rIfaces»
						Helper.assertNotNull(this.«ri.name.toFirstLower»);
					«ENDFOR»
					// TODO: Insert code here
				}
				
			«ENDFOR»
		«ENDFOR»
    '''
    
    def compileForComponentRequired(EList<Interface> l) '''
		«FOR ri : l»
			«ri.name» «ri.name.toFirstLower»;
		«ENDFOR»
		
		«FOR ri : l»
			public void set«ri.name»(«ri.name» «ri.name.toFirstLower»){ 
				Helper.assertNull(this.«ri.name.toFirstLower»);
				this.«ri.name.toFirstLower» = «ri.name.toFirstLower»;
			}
			
		«ENDFOR»
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
