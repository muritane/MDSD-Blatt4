package blatt1xtend;

import blatt1.Allocation.Allocation;
import blatt1.Allocation.AllocationContext;
import blatt1.Assembly.AssemblyConnector;
import blatt1.Assembly.AssemblyContext;
import blatt1.Assembly.DelegationConnector;
import blatt1.Assembly.Role;
import blatt1.Environment.Container;
import blatt1.Environment.Environment;
import blatt1.Environment.Link;
import blatt1.Repository.Behaviour.BehaviorDescription;
import blatt1.Repository.Component;
import blatt1.Repository.CompositeComponent;
import blatt1.Repository.Interface;
import blatt1.Repository.Service;
import blatt1.Repository.Types.Signature;
import blatt1.Repository.Types.Type;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class Blatt1Generator implements IGenerator {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    Iterable<Signature> _filter = Iterables.<Signature>filter(IteratorExtensions.<EObject>toIterable(resource.getAllContents()), Signature.class);
    for (final Signature o : _filter) {
      fsa.generateFile(
        "RemoveLater.java", 
        this.compile(o));
    }
  }
  
  public CharSequence compile(final Interface e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(" ");
    _builder.append("//war \"dispatch\" und auskommentiert - warum?");
    _builder.newLine();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(e.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("        ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(e.eContainer());
        _builder.append(_fullyQualifiedName_1, "        ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("        ");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public class ");
    String _name = e.getName();
    _builder.append(_name, "        ");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Signature> _signature = e.getSignature();
      for(final Signature f : _signature) {
        _builder.append("            ");
        CharSequence _compile = this.compile(f);
        _builder.append(_compile, "            ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Signature s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class RemoveLater {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    CharSequence _compile = this.compile(s.getReturnType());
    _builder.append(_compile, "\t");
    _builder.append(" ");
    String _name = s.getName();
    _builder.append(_name, "\t");
    _builder.append("(");
    {
      EList<Type> _parameterType = s.getParameterType();
      for(final Type p : _parameterType) {
        {
          boolean _contains = p.toString().contains("Void");
          boolean _not = (!_contains);
          if (_not) {
            CharSequence _compile_1 = this.compile(p);
            _builder.append(_compile_1, "\t");
          }
        }
      }
    }
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("// TODO: Insert code here");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    return _builder;
  }
  
  public CharSequence compile(final Type m) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _contains = m.toString().contains("Void");
      if (_contains) {
        _builder.append("void");
      }
    }
    return _builder;
  }
  
  public CharSequence compile(final blatt1.Repository.Types.Void m) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileComponent(final Component c) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Interface> _providedInterface = c.getProvidedInterface();
      for(final Interface pi : _providedInterface) {
        CharSequence _compile = this.compile(pi);
        _builder.append(_compile);
        _builder.append(" //interface compile wieder einführen - warum auskommentiert?");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Interface> _requiredInterface = c.getRequiredInterface();
      for(final Interface ri : _requiredInterface) {
        CharSequence _compile_1 = this.compile(ri);
        _builder.append(_compile_1);
        _builder.append(" //interface compile wieder einführen - warum auskommentiert?");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Service> _providedService = c.getProvidedService();
      for(final Service ps : _providedService) {
        CharSequence _compile_2 = this.compile(ps);
        _builder.append(_compile_2);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Service> _requiredService = c.getRequiredService();
      for(final Service rs : _requiredService) {
        CharSequence _compile_3 = this.compile(rs);
        _builder.append(_compile_3);
        _builder.newLineIfNotEmpty();
      }
    }
    CharSequence _compile_4 = this.compile(c.getBehaviourDescription());
    _builder.append(_compile_4);
    _builder.newLineIfNotEmpty();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(c.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit c.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(c.eContainer());
        _builder.append(_fullyQualifiedName_1, "            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence compile(final CompositeComponent c) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class ");
    String _name = c.getName();
    _builder.append(_name);
    _builder.append(" //TODO WARNING sollte das wirklich so sein? siehe behaviour description: es sollte doch nicht jede INSTANZ eine eige KLASSE haben, oder?");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _compileComponent = this.compileComponent(c);
    _builder.append(_compileComponent, "\t");
    _builder.newLineIfNotEmpty();
    {
      EList<AssemblyContext> _encapsulatedAssemblyContext = c.getEncapsulatedAssemblyContext();
      for(final AssemblyContext a : _encapsulatedAssemblyContext) {
        CharSequence _compile = this.compile(a);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Component c) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class ");
    String _name = c.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _compileComponent = this.compileComponent(c);
    _builder.append(_compileComponent, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.append("System.out.println(\"Component\");");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Service s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("    \t");
    _builder.append("public class ");
    String _name = s.getName();
    _builder.append(_name, "    \t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Signature> _correspondingSignature = s.getCorrespondingSignature();
      for(final Signature sign : _correspondingSignature) {
        CharSequence _compile = this.compile(sign);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t    ");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final BehaviorDescription b) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("    \t");
    _builder.append("public class BehaviorDescription //TODO WARNING - hat keinen namen -> kann nicht nach namen benannt werden -> siehe TODO WARNING oben - INSTANZ namen als KLASSEN namen?");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Service> _calledServices = b.getCalledServices();
      for(final Service s : _calledServices) {
        CharSequence _compile = this.compile(s);
        _builder.append(_compile);
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("//TODO branch loop etc?");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final blatt1.Assembly.System s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("    \t");
    _builder.append("public class System");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<AssemblyContext> _encapsulatedAssemblyContext = s.getEncapsulatedAssemblyContext();
      for(final AssemblyContext ac : _encapsulatedAssemblyContext) {
        CharSequence _compile = this.compile(ac);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Interface> _providedInterfaces = s.getProvidedInterfaces();
      for(final Interface pi : _providedInterfaces) {
        CharSequence _compile_1 = this.compile(pi);
        _builder.append(_compile_1);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final AssemblyConnector a) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class AssemblyConnector");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _compile = this.compile(a.getProvidedAssemblyContext());
    _builder.append(_compile, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _compile_1 = this.compile(a.getRequiredAssemblyContext());
    _builder.append(_compile_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _compile_2 = this.compile(a.getRequiredRole());
    _builder.append(_compile_2, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _compile_3 = this.compile(a.getProvidedRole());
    _builder.append(_compile_3, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final AssemblyContext a) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("    \t");
    _builder.append("public class ");
    String _name = a.getName();
    _builder.append(_name, "    \t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Role> _requiredRole = a.getRequiredRole();
      for(final Role rr : _requiredRole) {
        CharSequence _compile = this.compile(rr);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Role> _providedRole = a.getProvidedRole();
      for(final Role pr : _providedRole) {
        CharSequence _compile_1 = this.compile(pr);
        _builder.append(_compile_1);
        _builder.newLineIfNotEmpty();
      }
    }
    CharSequence _compile_2 = this.compile(a.getComponent());
    _builder.append(_compile_2);
    _builder.newLineIfNotEmpty();
    CharSequence _compile_3 = this.compile(a.getAllocationContext());
    _builder.append(_compile_3);
    _builder.newLineIfNotEmpty();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t    ");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Role r) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class ");
    String _name = r.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(r.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(r.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final DelegationConnector d) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("    \t");
    _builder.append("public class DelegationConnector");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    \t\t");
    CharSequence _compile = this.compile(d.getRole());
    _builder.append(_compile, "    \t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t\t");
    CharSequence _compile_1 = this.compile(d.getInterface());
    _builder.append(_compile_1, "    \t\t");
    _builder.newLineIfNotEmpty();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(d.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(d.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Allocation a) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class Allocation");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _compile = this.compile(a.getSystem());
    _builder.append(_compile, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _compile_1 = this.compile(a.getEnvironment());
    _builder.append(_compile_1, "\t");
    _builder.newLineIfNotEmpty();
    {
      EList<AllocationContext> _allocationContexts = a.getAllocationContexts();
      for(final AllocationContext ac : _allocationContexts) {
        CharSequence _compile_2 = this.compile(ac);
        _builder.append(_compile_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final AllocationContext a) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("    \t");
    _builder.append("public class AllocationContext");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Container> _targetContainer = a.getTargetContainer();
      for(final Container tc : _targetContainer) {
        CharSequence _compile = this.compile(tc);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<AssemblyContext> _allocatedAssemblyContext = a.getAllocatedAssemblyContext();
      for(final AssemblyContext ac : _allocatedAssemblyContext) {
        Object _compile_1 = this.compile(ac);
        _builder.append(_compile_1);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Container c) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class ");
    String _name = c.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Link> _links = c.getLinks();
      for(final Link l : _links) {
        CharSequence _compile = this.compile(l);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t\t");
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(c.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(c.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t\t\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Link l) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t    ");
    _builder.append("public class ");
    String _name = l.getName();
    _builder.append(_name, "\t    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Container> _linkedContainers = l.getLinkedContainers();
      for(final Container c : _linkedContainers) {
        Object _compile = this.compile(l);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(l.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(l.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Environment e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t    ");
    _builder.append("public class Environment");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    {
      EList<Container> _container = e.getContainer();
      for(final Container c : _container) {
        CharSequence _compile = this.compile(c);
        _builder.append(_compile);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Link> _link = e.getLink();
      for(final Link l : _link) {
        CharSequence _compile_1 = this.compile(l);
        _builder.append(_compile_1);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(e.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append(" //alles was für den Namen gebraucht wird? was mit s.name?");
        _builder.newLineIfNotEmpty();
        _builder.append("\t            ");
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(e.eContainer());
        _builder.append(_fullyQualifiedName_1, "\t            ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected void _compile(final Signature m, final IFileSystemAccess fsa) {
    this.compile(m.getReturnType(), fsa);
    EList<Type> _parameterType = m.getParameterType();
    for (final EObject o : _parameterType) {
      this.compile(o, fsa);
    }
    String _name = m.getName();
    String _plus = (_name + ".txt");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("this is element ");
    String _name_1 = m.getName();
    _builder.append(_name_1);
    _builder.newLineIfNotEmpty();
    fsa.generateFile(_plus, _builder);
  }
  
  protected void _compile(final Component m, final IFileSystemAccess fsa) {
    this.compile(m.getBehaviourDescription(), fsa);
    EList<Interface> _providedInterface = m.getProvidedInterface();
    for (final EObject o : _providedInterface) {
      this.compile(o, fsa);
    }
    EList<Service> _providedService = m.getProvidedService();
    for (final EObject o_1 : _providedService) {
      this.compile(o_1, fsa);
    }
    EList<Interface> _requiredInterface = m.getRequiredInterface();
    for (final EObject o_2 : _requiredInterface) {
      this.compile(o_2, fsa);
    }
    EList<Service> _requiredService = m.getRequiredService();
    for (final EObject o_3 : _requiredService) {
      this.compile(o_3, fsa);
    }
    String _name = m.getName();
    String _plus = (_name + ".txt");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("this is element ");
    String _name_1 = m.getName();
    _builder.append(_name_1);
    _builder.newLineIfNotEmpty();
    fsa.generateFile(_plus, _builder);
  }
  
  protected void _compile(final EObject m, final IFileSystemAccess fsa) {
  }
  
  public void compile(final EObject m, final IFileSystemAccess fsa) {
    if (m instanceof Component) {
      _compile((Component)m, fsa);
      return;
    } else if (m instanceof Signature) {
      _compile((Signature)m, fsa);
      return;
    } else if (m != null) {
      _compile(m, fsa);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(m, fsa).toString());
    }
  }
}
