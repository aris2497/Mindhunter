package simpleREST.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Profile("database")
@Component
public class GameDaoImpl implements GameDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game add(Game game) {

        final String sql = "INSERT INTO game(timeStarted, state, noOfGuesses, answer) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getTimeStarted());
            statement.setString(2, game.getState());
            statement.setInt(3, game.getNoOfGuesses());
            statement.setString(4, game.getAnswer());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }
    public Guess addGuess(Guess guess){
        final String sql = "INSERT INTO guess(result, time, gameId) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);


            statement.setString(1, guess.getResult());
            statement.setString(2, guess.getTime());
            statement.setInt(3, guess.getGameId());
            return statement;

        }, keyHolder);

        guess.setGuessNo(keyHolder.getKey().intValue());

        return guess;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT id, timeStarted, state, noOfGuesses, answer FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public List<Guess> getAllGuesses() {
        final String sql = "SELECT id, result, time, gameId FROM guess;";
        return jdbcTemplate.query(sql, new GuessMapper());
    }

    @Override
    public boolean update(Game game) {

        final String sql = "UPDATE game SET "
                + "timeStarted = ?, "
                + "state = ?, "
                + "noOfGuesses = ?, "
                + "answer = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                game.getTimeStarted(),
                game.getState(),
                game.getNoOfGuesses(),
                game.getAnswer(),
                game.getGameId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM game WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Game getGame(int id) {
        final String sql = "SELECT id, timeStarted, state, noOfGuesses, answer "
                + "FROM game WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }

    @Override
    public List<Guess> getGamesGuesses(Game game) {
        final String SELECT_GUESSES_FOR_GAME = "SELECT gu.* FROM guess gu "
                + "JOIN game ga ON gu.gameId = ga.id WHERE ga.id = ?";
        return jdbcTemplate.query(SELECT_GUESSES_FOR_GAME, new GuessMapper(),
                game.getGameId());
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gm = new Game();
            gm.setGameId(rs.getInt("id"));
            gm.setTimeStarted(rs.getString("timeStarted"));
            gm.setNoOfGuesses(rs.getInt("noOfGuesses"));
            gm.setAnswer(rs.getString("answer"));
            return gm;
        }
    }

    private static final class GuessMapper implements RowMapper<Guess> {
        @Override
        public Guess mapRow(ResultSet rs, int index) throws SQLException {
            Guess guess = new Guess();
            guess.setGuessNo(rs.getInt("id"));
            guess.setResult(rs.getString("result"));
            guess.setTime(rs.getString("time"));
            guess.setGameId(rs.getInt("gameId"));
            return guess;
        }
    }
}
