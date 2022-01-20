package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import dao.IDaoRemote;
import entities.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class UserUI {

	private static IDaoRemote lookUpUser() throws NamingException {
		Hashtable<Object, Object> config = new Hashtable<Object, Object>();
		config.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		config.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(config);
		return (IDaoRemote) context.lookup("ejb:/Géolocalisation-Server/UserService!dao.IDaoRemote");
	}

	private static IDaoRemote lookUpSmartphone() throws NamingException {
		Hashtable<Object, Object> config = new Hashtable<Object, Object>();
		config.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		config.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(config);
		return (IDaoRemote) context.lookup("ejb:/Geolocalisation-Server/SmartphoneService!dao.IDaoRemote");
	}

	private IDaoRemote<User> daoUser;
	private JFrame frmMachineUi;
	private JTextField nomTxt;
	private JTextField prenomTxt;
	private JTextField telephoneTxt;
	private JDateChooser dateChooser;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField emailTxt;

	// Remplissage de la JTable
	private void fillTable() throws RemoteException {

		List<User> users = daoUser.getAll();
		List<String[]> data = new ArrayList();

		for (User u : users) {
			data.add(new String[] { u.getNom(), u.getPrenom(), u.getTelephone(), u.getEmail(),
					String.valueOf(u.getDateNaissance()), String.valueOf(u.getId()) });
		}

		List<String> columns = new ArrayList<String>();
		columns.add("Nom");
		columns.add("Prénom");
		columns.add("Téléphone");
		columns.add("Email");
		columns.add("Date Naissance");
		columns.add("id");

		TableModel tableModel = new DefaultTableModel(data.toArray(new Object[][] {}), columns.toArray());
		table.setModel(tableModel);
		table.removeColumn(table.getColumnModel().getColumn(5));
	}

	// Vider les champs
	private void clearFields() {
		nomTxt.setText("");
		prenomTxt.setText("");
		telephoneTxt.setText("");
		emailTxt.setText("");
		dateChooser.setDate(null);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserUI window = new UserUI();
					window.frmMachineUi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			daoUser = lookUpUser();
		} catch (NamingException e3) {
			e3.printStackTrace();
		}

		frmMachineUi = new JFrame();
		frmMachineUi.setTitle("Client UI");
		frmMachineUi.setBounds(100, 100, 431, 431);
		frmMachineUi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMachineUi.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Gestion User");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 22, 400, 43);
		frmMachineUi.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(10, 90, 100, 14);
		frmMachineUi.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Pr\u00E9nom");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setBounds(150, 90, 100, 14);
		frmMachineUi.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("T\u00E9l\u00E9phone");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setBounds(290, 90, 100, 14);
		frmMachineUi.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Date de naissance");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setBounds(204, 146, 100, 14);
		frmMachineUi.getContentPane().add(lblNewLabel_4);

		nomTxt = new JTextField();
		nomTxt.setBounds(10, 107, 120, 20);
		frmMachineUi.getContentPane().add(nomTxt);
		nomTxt.setColumns(10);

		prenomTxt = new JTextField();
		prenomTxt.setBounds(150, 107, 120, 20);
		frmMachineUi.getContentPane().add(prenomTxt);
		prenomTxt.setColumns(10);

		telephoneTxt = new JTextField();
		telephoneTxt.setBounds(290, 107, 120, 20);
		frmMachineUi.getContentPane().add(telephoneTxt);
		telephoneTxt.setColumns(10);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(204, 171, 206, 20);
		frmMachineUi.getContentPane().add(dateChooser);

		JButton ajouterBtn = new JButton("Ajouter");
		ajouterBtn.setBounds(10, 358, 103, 23);
		frmMachineUi.getContentPane().add(ajouterBtn);

		JButton modifierBtn = new JButton("Modifier");
		modifierBtn.setBounds(150, 358, 103, 23);
		frmMachineUi.getContentPane().add(modifierBtn);

		JButton supprimerBtn = new JButton("Supprimer");
		supprimerBtn.setBounds(307, 358, 103, 23);
		frmMachineUi.getContentPane().add(supprimerBtn);

		table = new JTable() {
			// Désactiver l'édition de la JTable
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		try {
			fillTable();
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 202, 400, 145);
		frmMachineUi.getContentPane().add(scrollPane);

		emailTxt = new JTextField();
		emailTxt.setBounds(10, 171, 184, 20);
		frmMachineUi.getContentPane().add(emailTxt);
		emailTxt.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Email");
		lblNewLabel_5.setBounds(10, 146, 46, 14);
		frmMachineUi.getContentPane().add(lblNewLabel_5);

		// Bouton Ajouter
		ajouterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = nomTxt.getText();
				String prenom = prenomTxt.getText();
				String telephone = telephoneTxt.getText();
				String email = telephoneTxt.getText();
				Date dateNaissance = dateChooser.getDate();

				if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty()
						|| dateNaissance == null)
					JOptionPane.showConfirmDialog(null, "Tous les champs sont obligatoires!", "Problème",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				else {
					User user = new User(nom, prenom, telephone, email, dateNaissance);
					try {
						daoUser.create(user);
						fillTable();
						clearFields();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		// Bouton Modifier
		modifierBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int decision = JOptionPane.showConfirmDialog(null, "Modifier?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (decision == JOptionPane.YES_OPTION) {
					String nom = nomTxt.getText();
					String prenom = prenomTxt.getText();
					String telephone = telephoneTxt.getText();
					String email = emailTxt.getText();
					Date dateNaissance = dateChooser.getDate();

					if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty()
							|| dateNaissance == null)
						JOptionPane.showConfirmDialog(null, "Tous les champs sont obligatoires!", "Problème",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					else {
						User user = new User(nom, prenom, telephone, email, dateNaissance);

						int id = Integer.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 5).toString());
						user.setId(id);
						try {
							daoUser.update(user);
							fillTable();
							clearFields();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		// Bouton supprimer
		supprimerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int decision = JOptionPane.showConfirmDialog(null, "Supprimer?", "Confirmation",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if (decision == JOptionPane.YES_OPTION) {
					User user = new User();
					int id = Integer.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 5).toString());
					user.setId(id);

					try {
						daoUser.delete(user);
						fillTable();
						clearFields();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// Remplissage des champs en cliquant sur une ligne de la Jtable
		table.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				int id = Integer.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 5).toString());

				User user = daoUser.getById(id);

				nomTxt.setText(user.getNom());
				prenomTxt.setText(user.getPrenom());
				telephoneTxt.setText(String.valueOf(user.getTelephone()));
				emailTxt.setText(String.valueOf(user.getEmail()));
				dateChooser.setDate(user.getDateNaissance());

			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
}
