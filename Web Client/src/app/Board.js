import React from 'react';

import Square from './Square'

const style = {
  boardRow: {
    clear: "both",
    content: "",
    display: "table",
  },
  square: {
    background: "#fff",
    border: "1px solid #999",
    float: "left",
    fontSize: 24,
    fontWeight: "bold",
    lineHeight: 34,
    height: 34,
    marginRight: -1,
    marginTop: -1,
    padding: 0,
    textAlign: "center",
    width: 34,
  }
};

export default class Board extends React.Component {
  renderRow(i, row) {
    let j = 0;

    return row.map(() => {
      return (
        <Square
          key={i * 10 + j}
          value={this.props.squares[i][j++]}
        />
      )
    });
  }

  render() {
    let i = 0;

    const board = this.props.squares.map((row) => {
      return (
        <div style={style.boardRow}>
          {this.renderRow(i++, row)}
        </div>
      )
    });

    return (
      board
    );
  }
}
