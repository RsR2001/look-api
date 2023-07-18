package br.com.ifpe.look.modelo.cliente;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.look.modelo.acesso.UsuarioService;
import br.com.ifpe.look.util.entity.GenericService;
import br.com.ifpe.look.util.exception.EntidadeNaoEncontradaException;

/**
 * Classe responsável pelas operações relativos a um {@link Cliente}.
*/
@Service
public class ClienteService extends GenericService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private UsuarioService usuarioService;


    public List<Cliente> listarTodos() {
	
	    return repository.findAll();
    }

    public Cliente obterPorID(Long id) {

	    Optional<Cliente> consulta = repository.findById(id);
	
        if (consulta.isPresent()) {
            return consulta.get();
        } else {
            throw new EntidadeNaoEncontradaException("Cliente", id);
        }

    }

    @Transactional
    public Cliente save(Cliente cliente) {

        usuarioService.save(cliente.getUsuario());

        super.preencherCamposAuditoria(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado) {

	    Cliente cliente = repository.findById(id).get();
        cliente.setNome(clienteAlterado.getNome());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setFoneCelular(clienteAlterado.getFoneCelular());
        cliente.setFoneFixo(clienteAlterado.getFoneFixo());
	    
        super.preencherCamposAuditoria(cliente);
	    repository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {

        Cliente cliente = repository.findById(id).get();
	    cliente.setHabilitado(Boolean.FALSE);
	    super.preencherCamposAuditoria(cliente);

	    repository.save(cliente);
    }  
}