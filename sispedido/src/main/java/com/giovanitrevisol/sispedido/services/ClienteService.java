package com.giovanitrevisol.sispedido.services;

import com.giovanitrevisol.sispedido.domain.Cidade;
import com.giovanitrevisol.sispedido.domain.Cliente;
import com.giovanitrevisol.sispedido.domain.Endereco;
import com.giovanitrevisol.sispedido.domain.enums.Perfil;
import com.giovanitrevisol.sispedido.domain.enums.TipoCliente;
import com.giovanitrevisol.sispedido.dto.ClienteDTO;
import com.giovanitrevisol.sispedido.dto.ClienteNewDTO;
import com.giovanitrevisol.sispedido.repositories.ClienteRepository;
import com.giovanitrevisol.sispedido.repositories.EnderecoRepository;
import com.giovanitrevisol.sispedido.security.UserSS;
import com.giovanitrevisol.sispedido.services.exception.AuthorizationException;
import com.giovanitrevisol.sispedido.services.exception.DataIntegrateException;
import com.giovanitrevisol.sispedido.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && id.equals(user.getId()) ){
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Cliente não encontrado!!! - Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente insert(Cliente obj){
        obj.setId(null);
        obj = repo.save(obj);
       enderecoRepository.saveAll(obj.getEnderecos());
       return  obj;
    }

    public Cliente update(Cliente obj){
        Cliente newObj = this.find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        this.find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrateException("Não é possivel excluir porque há entidades relacionadas");
        }
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    public Cliente findByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }
        Cliente obj = repo.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
        }
        return obj;
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        return  repo.findAll(pageRequest);
        //neste return existe uma sobrecarga de metodos, ou seja estou chamando o mesmo metodos find passando
        //pageRequest por parametro.
    }

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDTO (ClienteNewDTO objDTO) {
        Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
        Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDTO.getTelefone1());
        if (objDTO.getTelefone2()!=null) {
            cli.getTelefones().add(objDTO.getTelefone2());
        }
        if (objDTO.getTelefone3()!=null) {
            cli.getTelefones().add(objDTO.getTelefone3());
        }
        return cli;
    }


        //atualiza os campos apenas que vieram da requisição
    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}

