package blatt1xtend;

import blatt1.Repository.Component;
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
  
  public CharSequence compile(final Component c) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("System.out.println(\"Component\");");
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
