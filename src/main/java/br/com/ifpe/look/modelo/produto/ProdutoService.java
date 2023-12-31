package br.com.ifpe.look.modelo.produto;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.look.util.entity.GenericService;


@Service
public class ProdutoService extends GenericService{
    
    @Autowired
    private ProdutoRepository repository;
 
    @Transactional
    public Produto save(Produto produto) {
 
        super.preencherCamposAuditoria(produto);
        return repository.save(produto);
    }

    @Transactional
    public void update(Long id, Produto produtoAlterado) {

        Produto produto = repository.findById(id).get();
        produto.setTitulo(produtoAlterado.getTitulo());
        produto.setCodigo(produtoAlterado.getCodigo());
        produto.setDescricao(produtoAlterado.getDescricao());
        produto.setValorUnitario(produtoAlterado.getValorUnitario());
        produto.setDataDeVencimento(produtoAlterado.getDataDeVencimento());

        super.preencherCamposAuditoria(produto);
        repository.save(produto);
    }

    public List<Produto> listarTodos() {

        return repository.findAll();
    }

    public Produto obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void delete(Long id) {

        Produto produto = repository.findById(id).get();
        produto.setHabilitado(Boolean.FALSE);
        super.preencherCamposAuditoria(produto);

        repository.save(produto);
    }

    public List<Produto> filtrar(String codigo, String titulo, Long idCategoria) {

        List<Produto> listaProdutos = repository.findAll();
 
        if ((codigo != null && !"".equals(codigo)) &&
            (titulo == null || "".equals(titulo)) &&
            (idCategoria == null)) {
                listaProdutos = repository.consultarPorCodigo(codigo);
        } else if (
            (codigo == null || "".equals(codigo)) &&
            (titulo != null && !"".equals(titulo)) &&
            (idCategoria == null)) {    
                listaProdutos = repository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
        } else if (
            (codigo == null || "".equals(codigo)) &&
            (titulo == null || "".equals(titulo)) &&
            (idCategoria != null)) {
                listaProdutos = repository.consultarPorCategoria(idCategoria); 
        } else if (
            (codigo == null || "".equals(codigo)) &&
            (titulo != null && !"".equals(titulo)) &&
            (idCategoria != null)) {
                listaProdutos = repository.consultarPorTituloECategoria(titulo, idCategoria); 
        }
 
        return listaProdutos;
 }
 
}