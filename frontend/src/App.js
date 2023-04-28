import logo from './logo.svg';
import './App.css'
import {Component} from "react";
import {Button, ButtonGroup, Input} from "reactstrap";

class App extends Component {
    state = {
        wallets: [],
        newWalletName: "",
        walletAmounts: {},
    };

    async componentDidMount() {
        const response = await fetch('/wallets');
        const body = await response.json();
        const amounts = {};
        for (const wallet of body) {
            amounts[wallet.id] = 0;
        }
        this.setState({wallets: body, walletAmounts: amounts});
    }

    async createWallet() {
        const {newWalletName, wallets} = this.state;
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: newWalletName,
        };
        const response = await fetch("/wallets", options);
        this.componentDidMount();
    }


    async deposit(walletId) {
        const {walletAmounts} = this.state;
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: walletAmounts[walletId],
        };
        const response = await fetch(`/wallets/${walletId}/deposit`, options);
        this.componentDidMount();
    }

    async withdraw(walletId) {
        const {walletAmounts} = this.state;
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: walletAmounts[walletId],
        };
        const response = await fetch(`/wallets/${walletId}/withdrawal`, options);
        this.componentDidMount();
    }

    render() {
        const {wallets, newWalletName, walletAmounts} = this.state;
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <div className="App-intro">
                        <h2>Wallets</h2>
                        {wallets.map(wallet =>
                            <tr key={wallet.id}>
                                <td>{wallet.name}</td>
                                <td>{wallet.balance}</td>
                                <td>
                                    <ButtonGroup>
                                        <Input type="number" value={walletAmounts[wallet.id]}
                                               onChange={(event) => this.setState({
                                                   walletAmounts: {
                                                       ...walletAmounts,
                                                       [wallet.id]: event.target.valueAsNumber
                                                   }
                                               })}/>
                                        <Button color="success" onClick={() => this.deposit(wallet.id)}>deposit</Button>
                                        <Button color="danger"
                                                onClick={() => this.withdraw(wallet.id)}>withdraw</Button>
                                    </ButtonGroup>
                                </td>
                            </tr>
                        )}
                        <ButtonGroup>
                            <Input
                                type="text"
                                placeholder="name"
                                value={newWalletName}
                                onChange={(event) => this.setState({newWalletName: event.target.value})}
                            />
                            <Button onClick={() => this.createWallet()}>Create new wallet</Button>
                        </ButtonGroup>
                    </div>
                </header>
            </div>
        );
    }
}

export default App;