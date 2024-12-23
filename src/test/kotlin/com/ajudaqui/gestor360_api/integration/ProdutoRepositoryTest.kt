package com.ajudaqui.gestor360_api.integration

import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.repository.ProductRepository
import com.ajudaqui.gestor360_api.repository.UsersRepository
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime
import kotlin.test.Test

@DataJpaTest
@Testcontainers// habilita o test de containers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// passando para o sprint não fazer a troca do nosso banco de dados por esse, esse não vai ser o pad~rao... por favor...
class ProdutoRepositoryTest() {

    @Autowired
    private lateinit var produtoRepository: ProductRepository//recurso so vai ser inicializado quando ele for ser utilizado

    @Autowired
    private lateinit var userRepository: UsersRepository

    companion object {

        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:15.3").apply {
            withDatabaseName("test_gestor360")
            withUsername("test")
            withPassword("test")
            withReuse(true) // Reaproveitar o container entre execuções para otimizar o tempo
        }

        @JvmStatic
        @BeforeAll
        fun startContainer() {
            postgresContainer.start() // Inicia o container antes de todos os testes
            System.setProperty("DB_URL", postgresContainer.jdbcUrl)
            System.setProperty("DB_USERNAME", postgresContainer.username)
            System.setProperty("DB_PASSWORD", postgresContainer.password)
        }

        @JvmStatic
        @AfterAll
        fun stopContainer() {
            postgresContainer.stop() // Para o container após todos os testes
        }
    }

        @Test
    fun `deve salvar e buscar um produto`() {
        val produto = Product(
            id = null,
            name = "Macarão Teste",
            items = listOf(),
            users = Users(id = 1, name = "Bob", email = "bob@email.com", password = "123456")
        )
            /*
            Por algum motivo ele ta lendo os daods do banco de produção,
            esta persistindo os dados no contaienr normal, mas esta somando com os de prod
            caso contrrio, nem o user id 1 eu teria acesso
             */

        val produtoSalvo = produtoRepository.save(produto)
        val produtoBuscado = produtoRepository.findById(produtoSalvo.id!!)

        assertThat(produtoBuscado).isPresent
        assertThat(produtoBuscado.get().name).isEqualTo("Macarão Teste")
    }

    @Test
    @Transactional
    fun `deve trazer produto pelo nome e pelo userId`() {


        val produto = Product(
            id = null,
            name = "Pão com passas",
            items = listOf(),
            users = Users(id = 1, name = "Bob", email = "bob@email.com", password = "123456"),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        produtoRepository.saveAll(
            mutableListOf(
                produto,
                produto.copy(name = "44"),
                produto.copy(name = "pão com ovo")
            )
        )

        val findAll =  produtoRepository.findAllByUserId(1)
        findAll.map { it.id }.also { println(it) }
        findAll.map { it.name }.also { println(it) }

        assertThat(findAll.size).isEqualTo(3)

    }


}