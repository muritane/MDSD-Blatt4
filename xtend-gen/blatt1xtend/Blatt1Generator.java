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
import blatt1.Repository.Interface;
import blatt1.Repository.Service;
import blatt1.Repository.Types.Signature;
import blatt1.Repository.Types.Type;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class Blatt1Generator implements IGenerator {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    fsa.generateFile("repository/Helper.java", this.helper());
    Iterable<Interface> _filter = Iterables.<Interface>filter(IteratorExtensions.<EObject>toIterable(resource.getAllContents()), Interface.class);
    for (final Interface o : _filter) {
      String _string = this._iQualifiedNameProvider.getFullyQualifiedName(o).toString("/");
      String _plus = ("repository/" + _string);
      String _plus_1 = (_plus + ".java");
      fsa.generateFile(_plus_1, 
        this.compile(o));
    }
    Iterable<Component> _filter_1 = Iterables.<Component>filter(IteratorExtensions.<EObject>toIterable(resource.getAllContents()), Component.class);
    for (final Component o_1 : _filter_1) {
      String _name = o_1.getName();
      String _plus_2 = (_name + "/");
      String _string_1 = this._iQualifiedNameProvider.getFullyQualifiedName(o_1).toString("/");
      String _plus_3 = (_plus_2 + _string_1);
      String _plus_4 = (_plus_3 + ".java");
      fsa.generateFile(_plus_4, 
        this.compile(o_1));
    }
  }
  
  public CharSequence helper() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package repository;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.junit.Assert;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class Helper {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static void assertNotNull(Object o) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Assert.assertNotNull(o);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static void assertNull(Object o) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Assert.assertNull(o);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Interface e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package repository;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public interface ");
    String _name = e.getName();
    _builder.append(_name);
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    {
      EList<Signature> _signature = e.getSignature();
      for(final Signature f : _signature) {
        _builder.append("    ");
        CharSequence _compile = this.compile(f);
        _builder.append(_compile, "    ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Signature s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    CharSequence _compile = this.compile(s.getReturnType());
    _builder.append(_compile);
    _builder.append(" ");
    String _name = s.getName();
    _builder.append(_name);
    _builder.append("(");
    CharSequence _compile_1 = this.compile(s.getParameterType());
    _builder.append(_compile_1);
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence compile(final EList<Type> l) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Type p : l) {
        {
          boolean _contains = p.toString().contains("Void");
          boolean _not = (!_contains);
          if (_not) {
            CharSequence _compile = this.compile(p);
            _builder.append(_compile);
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Type _last = IterableExtensions.<Type>last(l);
          boolean _notEquals = (!Objects.equal(p, _last));
          if (_notEquals) {
            _builder.append(",");
            _builder.newLine();
          }
        }
      }
    }
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
    _builder.append("voidS");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileForComponent(final EList<Interface> l) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Interface pi : l) {
        String _name = pi.getName();
        _builder.append(_name);
        {
          Interface _last = IterableExtensions.<Interface>last(l);
          boolean _notEquals = (!Objects.equal(pi, _last));
          if (_notEquals) {
            _builder.append(", ");
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence compile(final Component c) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _name = c.getName();
    _builder.append(_name);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Interface> _providedInterface = c.getProvidedInterface();
      for(final Interface pi : _providedInterface) {
        _builder.append("import repository.");
        String _name_1 = pi.getName();
        _builder.append(_name_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Interface> _requiredInterface = c.getRequiredInterface();
      for(final Interface ri : _requiredInterface) {
        _builder.append("import repository.");
        String _name_2 = ri.getName();
        _builder.append(_name_2);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      int _size = c.getRequiredInterface().size();
      boolean _tripleNotEquals = (_size != 0);
      if (_tripleNotEquals) {
        _builder.append("import repository.Helper;");
        _builder.newLine();
      }
    }
    _builder.append("        ");
    _builder.newLine();
    _builder.append("public class ");
    String _name_3 = c.getName();
    _builder.append(_name_3);
    _builder.append(" implements ");
    CharSequence _compileForComponent = this.compileForComponent(c.getProvidedInterface());
    _builder.append(_compileForComponent);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _compileForComponentRequired = this.compileForComponentRequired(c.getRequiredInterface());
    _builder.append(_compileForComponentRequired, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _compileForComponentProvided = this.compileForComponentProvided(c.getProvidedInterface(), c.getRequiredInterface());
    _builder.append(_compileForComponentProvided, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileForComponent(final Signature s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    CharSequence _compile = this.compile(s.getReturnType());
    _builder.append(_compile);
    _builder.append(" ");
    String _name = s.getName();
    _builder.append(_name);
    _builder.append("(");
    CharSequence _compile_1 = this.compile(s.getParameterType());
    _builder.append(_compile_1);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("// TODO: Insert code here");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileForComponentProvided(final EList<Interface> pIfaces, final EList<Interface> rIfaces) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Interface pi : pIfaces) {
        {
          EList<Signature> _signature = pi.getSignature();
          for(final Signature s : _signature) {
            _builder.append("// Implementing ");
            String _name = s.getName();
            _builder.append(_name);
            _builder.append(" from interface ");
            String _name_1 = pi.getName();
            _builder.append(_name_1);
            _builder.newLineIfNotEmpty();
            _builder.append("@Override");
            _builder.newLine();
            _builder.append("public ");
            CharSequence _compile = this.compile(s.getReturnType());
            _builder.append(_compile);
            _builder.append(" ");
            String _name_2 = s.getName();
            _builder.append(_name_2);
            _builder.append("(");
            CharSequence _compile_1 = this.compile(s.getParameterType());
            _builder.append(_compile_1);
            _builder.append(") {");
            _builder.newLineIfNotEmpty();
            {
              for(final Interface ri : rIfaces) {
                _builder.append("\t");
                _builder.append("Helper.assertNotNull(this.");
                String _firstLower = StringExtensions.toFirstLower(ri.getName());
                _builder.append(_firstLower, "\t");
                _builder.append(");");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("\t");
            _builder.append("// TODO: Insert code here");
            _builder.newLine();
            _builder.append("}");
            _builder.newLine();
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence compileForComponentRequired(final EList<Interface> l) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Interface ri : l) {
        String _name = ri.getName();
        _builder.append(_name);
        _builder.append(" ");
        String _firstLower = StringExtensions.toFirstLower(ri.getName());
        _builder.append(_firstLower);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      for(final Interface ri_1 : l) {
        _builder.append("public void set");
        String _name_1 = ri_1.getName();
        _builder.append(_name_1);
        _builder.append("(");
        String _name_2 = ri_1.getName();
        _builder.append(_name_2);
        _builder.append(" ");
        String _firstLower_1 = StringExtensions.toFirstLower(ri_1.getName());
        _builder.append(_firstLower_1);
        _builder.append("){ ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("Helper.assertNull(this.");
        String _firstLower_2 = StringExtensions.toFirstLower(ri_1.getName());
        _builder.append(_firstLower_2, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("this.");
        String _firstLower_3 = StringExtensions.toFirstLower(ri_1.getName());
        _builder.append(_firstLower_3, "\t");
        _builder.append(" = ");
        String _firstLower_4 = StringExtensions.toFirstLower(ri_1.getName());
        _builder.append(_firstLower_4, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence compile(final Service s) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
    _builder.append("\t    ");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final BehaviorDescription b) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(b.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(b.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(s.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final AssemblyConnector a) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("public class AssemblyConnector");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    \t\t");
    CharSequence _compile = this.compile(a.getProvidedAssemblyContext());
    _builder.append(_compile, "    \t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t\t");
    CharSequence _compile_1 = this.compile(a.getRequiredAssemblyContext());
    _builder.append(_compile_1, "    \t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t\t");
    CharSequence _compile_2 = this.compile(a.getRequiredRole());
    _builder.append(_compile_2, "    \t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t\t");
    CharSequence _compile_3 = this.compile(a.getProvidedRole());
    _builder.append(_compile_3, "    \t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final AssemblyContext a) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
        _builder.append("\t");
        CharSequence _compile_1 = this.compile(pr);
        _builder.append(_compile_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _compile_2 = this.compile(a.getComponent());
    _builder.append(_compile_2, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _compile_3 = this.compile(a.getAllocationContext());
    _builder.append(_compile_3, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Role r) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(r.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(r.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("public class ");
    String _name = r.getName();
    _builder.append(_name, "    \t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final DelegationConnector d) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(d.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(d.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Allocation a) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t ");
    _builder.append("public class Allocation");
    _builder.newLine();
    _builder.append("    \t ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    \t \t");
    CharSequence _compile = this.compile(a.getSystem());
    _builder.append(_compile, "    \t \t");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t \t");
    CharSequence _compile_1 = this.compile(a.getEnvironment());
    _builder.append(_compile_1, "    \t \t");
    _builder.newLineIfNotEmpty();
    {
      EList<AllocationContext> _allocationContexts = a.getAllocationContexts();
      for(final AllocationContext ac : _allocationContexts) {
        CharSequence _compile_2 = this.compile(ac);
        _builder.append(_compile_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t ");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final AllocationContext a) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(a.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
        _builder.append("\t");
        Object _compile_1 = this.compile(ac);
        _builder.append(_compile_1, "\t");
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
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(c.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(c.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    \t");
    _builder.append("public class ");
    String _name = c.getName();
    _builder.append(_name, "    \t");
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
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Link l) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(l.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(l.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compile(final Environment e) {
    StringConcatenation _builder = new StringConcatenation();
    {
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(e.eContainer());
      boolean _tripleNotEquals = (_fullyQualifiedName != null);
      if (_tripleNotEquals) {
        _builder.append("package ");
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(e.eContainer());
        _builder.append(_fullyQualifiedName_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
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
        _builder.append("\t");
        CharSequence _compile_1 = this.compile(l);
        _builder.append(_compile_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
      }
    }
    _builder.append("»");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected void _compile(final EObject m, final IFileSystemAccess fsa) {
  }
  
  public void compile(final EObject m, final IFileSystemAccess fsa) {
    _compile(m, fsa);
    return;
  }
}
