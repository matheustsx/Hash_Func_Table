//Um hash é uma função que transforma uma sequência de dados de tamanho que voce escolheu 
//em uma sequência de dados de tamanho fixo. Essa sequência de dados de tamanho fixo é chamada de 
//valor hash ou código hash.

//--Identificação de dados: Um hash pode ser usado para identificar um conjunto de dados de forma única. 
//Por exemplo, um hash pode ser usado para identificar um arquivo ou uma mensagem.

//--Verificação de integridade: Um hash pode ser usado para verificar se um conjunto de dados foi alterado. 
//Se o hash do conjunto de dados 
//original for diferente do hash do conjunto de dados modificado, então o conjunto de dados foi alterado.

//--Criptografia: Um hash pode ser usado para gerar uma chave criptográfica.

package structure_data;

import java.util.List;

//Código Java que implementa uma Estrutura de Dados de Conjunto com espalhamento usando uma tabela hash.
import java.util.ArrayList;
import java.util.LinkedList;

//Declara uma classe chamada ConjuntoEspalhamento.
public class ConjuntoEspalhamento {
	
	//Declara uma variável 'tabela' que é uma lista de listas. Cada lista interna terá uma lista encadeada de 
	//strings. Ai onde os elementos estarão armazenados.
	private ArrayList<LinkedList<String>> tabela = 
			new ArrayList<LinkedList<String>>();
	
	//Método privado que calcula o indice onde uma palavra deve ser inserida na tabela hash.
	//Ele usa a função calculaCodigoDeEspalhamento para gerar um indice para cada palavra.
	//Como o valor do indice pode vir negativo, então ele usa o math.abs para definir um
	//valor absoluto, para ser sempre positivo. Depois disso aplica uma operação de módulo
	//para encontrar o indice na lista e onde a palavra deve ser armazenada.
	private int calculaIndiceDaTabela(String palavra) {
		int codigoDeEspalhamento = this.calculaCodigoDeEspalhamento(palavra);
		codigoDeEspalhamento = Math.abs(codigoDeEspalhamento);
		return codigoDeEspalhamento % this.tabela.size();
	}
	
	//Gera um indice unico para cada palavra
	//Ele usa um valor aleatorio como o 31 para que nao ocorra colisões entre as palavras
	//Por exemplo, se voce trocar apenas duas letras de uma determinada palavra, ela ainda
	//pode ter o mesmo valor.
	private int calculaCodigoDeEspalhamento(String palavra) {
		int codigo = 1;
		for (int i = 0; i < palavra.length(); i++) {
			codigo = 31 * codigo + palavra.charAt(i);
		}
		//Abaixo é o hashcode que permite de forma rapida a identificação e recuperação de objetos
		//em estrutura de dados
		return codigo;
	}
	
	//Redimensiona a tabela para uma nova capacidade 
	//Primeiro ele pega todas as palavras e depois limpa a tabela
	//Em seguida adiciona as palavras com a nova capacidade da tabela.
	private void redimensionaTabela(int novaCapacidade) {
		List<String> palavras = this.pegaTodas();
		this.tabela.clear();
		
		for (int i = 0; i < novaCapacidade; i++) {
			this.tabela.add(new LinkedList<String>());
		}
		
		for (String palavra : palavras) {
			this.adiciona(palavra);
		}
	}
	
	//Verifica a carga atual da tabela. Se ela for maior que 0.75 então a tabela 
	//dobra o seu tamanho. Caso ela seja menor que 0.25 então o valor da tabela é dividida na metade 
	//Mas o minimo é de 10 elementos.
	private void verificaCarga() {
		int capacidade = this.tabela.size();
		double carga = (double) this.tamanho / capacidade;
		
		if (carga > 0.75) {
			this.redimensionaTabela(capacidade * 2);
		} else if (carga < 0.25) {
			this.redimensionaTabela(Math.max(capacidade / 2, 10));
		}
	}
	
	//variavel tamanho definida para acompanhar o numero de elementos atualmente armazenados
	//no conjunto.
	private int tamanho = 0;
	
	//construtor da classe. inicia a tabela com 26 listas vazias, uma para cada letra do alfabeto
	//o LinkedList cria uma nova lista encadeada de strings e após essas listas serem criadas
	//são adicionadas na tabela
	public ConjuntoEspalhamento() {
		for (int i = 0; i < 26; i++) {
			LinkedList<String> lista = new LinkedList<String>();
			tabela.add(lista);
		}
	}
	
	//Imprime o conteudo da tabela
	public void imprimeTabela() {
		for (List<String> lista : this.tabela) {
			System.out.println("[");
			for (int i = 0; i < lista.size(); i++) {
				System.out.println("*");
			}
			System.out.println("]");
		}
	}
	
	//É usado para adicionar uma nova palavra ao conjunto. Verifica se a palavra ja está presente
	//verifica a carga e se necessario redimensiona a tabela, calcula o indice onde deve ser adicionada 
	//e em seguida atualiza o tamanho do conjunto
	public void adiciona(String palavra) {
		if (!this.contem(palavra)) {
			this.verificaCarga();
			int indice = this.calculaIndiceDaTabela(palavra);
			List<String> lista = this.tabela.get(indice);
			lista.add(palavra);
			this.tamanho++;
		}
	}
	
	//É usado para remover alguma palavra do conjunto. Verifica se existe alguma palavra presente
	//Calcula o indice onde está localizada, remove da lista e atualiza o tamanho do conjunto.
	//Depois verifica a carga e se necessario redimensiona a tabela.
	public void remove(String palavra) {
		if (this.contem(palavra)) {
			int indice = this.calculaIndiceDaTabela(palavra);
			List<String> lista = this.tabela.get(indice);
			lista.remove(palavra);
			this.tamanho--;
			this.verificaCarga();
		}
	}
	
	//Verifica se a palavra está presente no conjunto, calcula o indice e 
	//depois verifica se ela está na lista.
	public boolean contem(String palavra) {
		int indice = this.calculaIndiceDaTabela(palavra);
		//É usada para recuperar a lista encadeada de strings que representa uma das "posições" da tabela hash. 
		List<String> lista = this.tabela.get(indice);
		
		return lista.contains(palavra);
	}
	
	//Retorna a lista com todas as palavras do conjunto.
	public List<String> pegaTodas() {
		List<String> palavras = new ArrayList<String>();
		
		for (int i = 0; i < this.tabela.size(); i++) {
			palavras.addAll(this.tabela.get(i));
		}
		
		return palavras;
	}
	
	//Simplesmente retorna o número de elementos atualmente armazenados no conjunto.
	 public int tamanho() {
		return this.tamanho;
	 }
}
