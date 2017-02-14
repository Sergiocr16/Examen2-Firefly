'use strict';

describe('Controller Tests', function() {

    describe('Reserva Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockReserva, MockReservaTipo, MockUsuario;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockReserva = jasmine.createSpy('MockReserva');
            MockReservaTipo = jasmine.createSpy('MockReservaTipo');
            MockUsuario = jasmine.createSpy('MockUsuario');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Reserva': MockReserva,
                'ReservaTipo': MockReservaTipo,
                'Usuario': MockUsuario
            };
            createController = function() {
                $injector.get('$controller')("ReservaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coffeeShopApp:reservaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
