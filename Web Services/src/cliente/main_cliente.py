from menu_cliente import MenuCliente
from cliente_http import ClienteHttp

class MainCliente:
    """Initial class for project with the main function"""

    

    def run(self):

            self.username: str = "john snow"
            server_url: str

            self.cliente:ClienteHttp = ClienteHttp()
        
            self.menu: MenuCliente = MenuCliente(self.username, self.cliente)
            self.menu.start_menu()
        
            print ("Hello, world!")

if __name__ == '__main__':
    MainCliente().run()