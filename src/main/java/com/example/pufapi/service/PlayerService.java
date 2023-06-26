package com.example.pufapi.service;

import com.example.pufapi.api.model.DbHandler;
import com.example.pufapi.api.model.Figure;
import com.example.pufapi.api.model.Player;
import com.example.pufapi.api.model.Playerhistory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class PlayerService {
    static private DbHandler dbHandler;
    static private Connection connection;
    static private PreparedStatement preparedStatement;
    private List<Player> playerlist;

    public PlayerService() throws SQLException, ClassNotFoundException {
        dbHandler = new DbHandler();
        connection = dbHandler.getDbConnection();
    }

    public static int writeToDB(String playername) throws SQLException {
        String insert = "INSERT INTO player(playername, highscore) " + "VALUES(?,?)";

        preparedStatement = connection.prepareStatement(insert);
//        preparedStatement.setInt(1,1);
        preparedStatement.setString(1, playername);
        preparedStatement.setInt(2, 0);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result;
    }


    public static String validatePlayer(String playername) throws SQLException {
        String query = "SELECT * FROM player where playername = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playername);
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonObject j = new JsonObject();
            if (resultSet.next()) {
                j.addProperty("playername", resultSet.getString("playername"));
                j.addProperty("highscore", resultSet.getInt("highscore"));
//                j.addProperty("img_path", resultSet.getString("img_path"));
                System.out.println(j);
            } else {
                j.addProperty("playername", playername);
            }
            writeToDB(playername);
            return j.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Optional getPlayer(String playername) {
        Optional optional = Optional.empty();
        try {
            String s = getDatabyColumn("player", "playername", playername);

            if (s != null) {
                optional = optional.of(new Gson().fromJson(s, Player[].class)[0]);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optional;
    }

    public static boolean updateHighscore(String playername, Integer highscore) {
        String query = "UPDATE player SET highscore = ? where playername = ?";
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setInt(1, highscore);
            preparedStatement1.setString(2, playername);
            int result = preparedStatement1.executeUpdate();
            preparedStatement1.close();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional getFigure(String figname) {
        Optional optional = Optional.empty();
        try {
            String s = getDatabyColumn("figures", "figurename", figname);

            if (s != null) {
                optional = optional.of(new Gson().fromJson(s, Figure[].class)[0]);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optional;
    }

    public Optional getPlayerHistory(String playername) {
        Optional optional = Optional.empty();
        try {
            String s = getDatabyColumn("history", "playername", playername);

            if (s != null) {
                optional = optional.of(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optional;
    }

//    public static Figure getFig(String figurename) throws SQLException {
//        String query = "SELECT * FROM figures where figurename = ? ";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, figurename);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                Figure f = new Figure(figurename);
//                f.setImg(resultSet.getBlob("figureimg"));
//
//                return f;
//            }
//            writeToDB(figurename);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public static String getDatabyColumn(String table, String column, String value) throws SQLException {
        String query = "SELECT * FROM " + table + " where " + column + " = ? ";
        Gson g = new Gson();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
//            JsonObject jo = new JsonObject();
            JsonArray ja = new JsonArray();
            while (resultSet.next()) {
                JsonObject j = new JsonObject();
                int count = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= count; i++) {
                    String s = resultSet.getMetaData().getColumnName(i);
                    int type = resultSet.getMetaData().getColumnType(i);
                    System.out.println("+++++++++++++++++++++++++++++++" + s + "|" + type);
                    switch (type) {
                        case 4:
                            j.addProperty(s, resultSet.getInt(s));
                            break;
                        case -4:
                            String blob = Base64.getEncoder().encodeToString(resultSet.getBlob(s).getBinaryStream().readAllBytes());

//                            System.out.println("---------------------" + Arrays.toString(resultSet.getBlob(s).getBinaryStream().readAllBytes()));
                            j.addProperty(s, blob);
                            break;
                        case -5:
                            j.addProperty(s, resultSet.getLong(s));
                            break;
                        default:
                            j.addProperty(s, resultSet.getString(s));
                    }
                }
                System.out.println(j);
                ja.add(j);
            }
//            jo.add("dataList", ja);
//            System.out.println("---------------------" + ja);
            return new Gson().toJson(ja);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean writePlayerHistory(Playerhistory ph) {
        String insert = "INSERT INTO history(playername, highscore, date, result) " + "VALUES(?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, ph.getPlayername());
            preparedStatement.setInt(2, ph.getHighscore());
            preparedStatement.setLong(3, ph.getDate());
            preparedStatement.setString(4, ph.getResult());

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        preparedStatement.setInt(1,1);
    }

    public static boolean deletePlayer(String playername) {
        String query = "DELETE FROM player where playerid = ?";
        boolean result = false;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, playername);
            result = preparedStatement1.execute();
            preparedStatement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean deleteHistory(String playername) {
        String query = "DELETE FROM history where playername = ?";
        boolean result = false;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, playername);
            result = preparedStatement1.execute();
            preparedStatement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
